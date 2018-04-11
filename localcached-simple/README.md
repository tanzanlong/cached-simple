http://blog.csdn.net/vtopqx/article/details/76382934

https://issues.apache.org/jira/projects/ROCKETMQ/issues/ROCKETMQ-204?filter=allopenissues

https://www.zhihu.com/question/30195969 rocketmq怎么保证队列完全顺序消费？


消息中间件需要解决的问题

1.发布订阅 （一对多，一对一，广播）

2.消息优先级

3.消息顺序

4.消息过滤

5.消息持久化

6.消息可靠性

7.低延时

8 每个消息必须投递一次-at least once（ack机制）

9 Exactly Only Once
(1). 发送消息阶段，不允许发送重复的消息。
(2). 消费消息阶段，不允许消费重复的消息

10 broker buffer满了策略

11 回朔消费

12消息堆积

13 分布式事务消息

14 定时消息

15 消息重试。




producer实现

消息发送

1.校验服务器状态

2.校验消息合法性
 .checkTopic校验topic的合法性 是否有特殊字符 长度不大于255，是否为系统默认主题TBW102
 . 消息体 body 不大于1024 * 1024 * 4; // 4M
 . 获取路由信息
 . 获得发送失败循环发送次数（同步发送次数为设置的 producer.setRetryTimesWhenSendFailed(3)+1,其他模式为1）
 #进入循环
 . 选择队列（默认根据容错延时信息表来选择队列）
 .调用核心发送接口发送消息
 .更新容错延时信息


broker实现（消息中转角色，负责存储消息，转发消息，一般也称为 Server。在 JMS 规范中称为 Provider。但是RocketMQ的Broker和JMS1.1定义的不太一样,比如JMS中P2P消息消费过后会删除.https://segmentfault.com/a/1190000011003778）
消息接收

Broker 异常情况下怎么保证数据可靠性?
异常情况:

1. Broker 正常关闭
2. Broker 异常 Crash
3. OS Crash
4. 机器掉电，但是能立即恢复供电情况。
5. 机器无法开机(可能是cpu、主板、内存等关键设备损坏)
6. 磁盘设备损坏。

1-4种情况都属于硬件资源可立即恢复情况，RocketMQ在这四种情况下能保证消息不丢，或者丢失少量数据(依赖刷盘方式是同步还是异步).
5-6属于单点故障，且无法恢复，一旦发生，在此单点上的消息全部丢失。RocketMQ 在这两种情况下，通过异步复制，可保证99%的消息不丢，但是仍然会有极少量的消息可能丢失。通过同步双写技术可以完全避免单点，同步双写势必会影响性能，适合对消息可靠性要求极高的场合，例如与Money相关的应用。

刷盘策略:
RocketMQ 的所有消息都是持久化的，先写入系统PAGECACHE，然后刷盘，可以保证内存与磁盘都有一份数据，访问时，直接从内存读取。
异步刷盘:

在有 RAID 卡，SAS 15000 转磁盘测试顺序写文件，速度可以达到 300M 每秒左右，而线上的网卡一般都为千兆网卡，写磁盘速度明显快于数据网络入口速度，那么是否可以做到写完内存就向用户返回，由后台线程刷盘呢?
(1). 由于磁盘速度大于网卡速度，那么刷盘的进度肯定可以跟上消息的写入速度。

(2). 万一由于此时系统压力过大，可能堆积消息，除了写入 IO，还有读取 IO，万一出现磁盘读取落后情况，

会不会导致系统内存溢出，答案是否定的，原因如下:
a) 写入消息到 PAGECACHE 时，如果内存不足，则尝试丢弃干净的 PAGE，腾出内存供新消息使用，策略是 LRU 方式。
b) 如果干净页不足，此时写入PAGECACHE会被阻塞，系统尝试刷盘部分数据，大约每次尝试 32 个 PAGE，异步刷盘:消息收到后,返回Producer Ok,同时调用消息存储过程,异常情况下,可能造成少量数据丢失来找出更多干净 PAGE。

综上，内存溢出的情况不会出现
同步刷盘:

>同步刷盘与异步刷盘的唯一区别是异步刷盘写完 PAGECACHE 直接返回，而同步刷盘需要等待刷盘完成才返回， 同步刷盘流程如下:
(1). 写入 PAGECACHE 后，线程等待，通知刷盘线程刷盘。 
(2). 刷盘线程刷盘后，唤醒前端等待线程，可能是一批线程。 
(3). 前端等待线程向用户返回成功。




Broker 怎么保证存储高吞吐量?
Broker系统的瓶颈在IO操作,RocketMQ使用的时文件存储的方式,使用Java NIO的内存直接映射避免了文件到系统调用再到用户空间的两次调用,根据kafka官方文档可以达到600M/s.上述代码中的MappedByteBuffer就是内存映射文件的Java实现.
关于NIO,可以参见Java NIO-(https://segmentfault.com/a/1190000010858641)



Broker 消息堆积应该怎么处理?
消息中间件的主要功能是异步解耦，还有个重要功能是挡住前端的数据洪峰，保证后端系统的稳定性，这就要 求消息中间件具有一定的消息堆积能力，消息堆积分以下两种情况:

消息堆积在内存 Buffer，一旦超过内存 Buffer，可以根据一定的丢弃策略来丢弃消息，如 CORBA Notification 规范中描述。适合能容忍丢弃消息的业务，这种情况消息的堆积能力主要在于内存 Buffer 大小，而且消息 堆积后，性能下降不会太大，因为内存中数据多少对于对外提供的访问能力影响有限。

消息堆积到持久化存储系统中，例如DB，KV存储，文件记录形式。
当消息不能在内存 Cache 命中时，要不可避免的访问磁盘，会产生大量读 IO，读 IO 的吞吐量直接决定了 消息堆积后的访问能力。

评估消息堆积能力主要有以下四点:

消息能堆积多少条，多少字节?即消息的堆积容量。

依赖磁盘大小

b. 消息堆积后，发消息的吞吐量大小，是否会受堆积影响?
无 SLAVE 情况，会受一定影响 
有 SLAVE 情况，不受影响

c. 消息堆积后，正常消费的Consumer是否会受影响?
无 SLAVE 情况，会受一定影响 
有 SLAVE 情况，不受影响

d . 消息堆积后，访问堆积在磁盘的消息时，吞吐量有多大?
与访问的并发有关，最慢会降到 5000 左右。

在有 Slave 情况下，Master 一旦发现 Consumer 访问堆积在磁盘的数据时，会向 Consumer 下达一个重定向指令，令 Consumer从Slave拉取数据，这样正常的发消息与正常消费的Consumer都不会因为消息堆积受影响，因为系统将堆积场景与非堆积场景分割在了两个不同的节点处理。这里会产生另一个问题，Slave会不会写性能下降，答案是否定的。因为 Slave 的消息写入只追求吞吐量，不追求实时性，只要整体的吞吐量高就可以，而 Slave每次都是从Master拉取一批数据，如1M，这种批量顺序写入方式即使堆积情况，整体吞吐量影响相对较小，只是写入 RT 会变长。



Broker 的buffer满了怎么办?
Broker 的 Buffer 通常指的是 Broker 中一个队列的内存 Buffer 大小，这类 Buffer 通常大小有限，如果 Buffer 满 了以后怎么办?

CORBA Notification 规范
(1). RejectNewEvents
拒绝新来的消息，向 Producer 返回 RejectNewEvents 错误码。 
(2). 按照特定策略丢弃已有消息
a) AnyOrder - Any event may be discarded on overflow. This is the default setting for this property.
b) FifoOrder - The first event received will be the first discarded.
c) LifoOrder - The last event received will be the first discarded.
d) PriorityOrder - Events should be discarded in priority order,
such that lower priority,events will be discarded before higher priority events.
e) DeadlineOrder - Events should be discarded in the order of shortest expiry deadline first.

RocketMQ 没有内存Buffer概念，RocketMQ的队列都是持久化磁盘，数据定期清除。
对于此问题的解决思路，RocketMQ 同其他 MQ 有非常显著的区别，RocketMQ 的内存 Buffer 抽象成一个无限长度的队列，不管有多少数据进来都能装得下，这个无限是有前提的，Broker 会定期删除过期的数据，例如 Broker 只保存3天的消息，那么这个Buffer虽然长度无限，但是3天前的数据会被从队尾删除。

Broker HA怎么实现的?
在刷盘后有与Slave通信的逻辑,具体调用HAService中的服务,只是个tcp请求,逻辑比较简单,就不再详细分析.这里值得一提的是,这里竟然没有委托netty实现,而使用原始的Java NIO请求和处理.




Broker 和Name Server的事务消息怎么支持的?
1. Producer向Broker发送1条类型为TransactionPreparedType的消息，Broker接收消息保存在CommitLog中，然后返回消息的queueOffset和MessageId到Producer，MessageId包含有commitLogOffset（即消息在CommitLog中的偏移量，通过该变量可以直接定位到消息本身），由于该类型的消息在保存的时候，commitLogOffset没有被保存到consumerQueue中，此时客户端通过consumerQueue取不到commitLogOffset，所以该类型的消息无法被取到，导致不会被消费。
2. Producer端的TransactionExecuterImpl执行本地操作，返回本地事务的状态，然后发送一条类型为TransactionCommitType或者TransactionRollbackType的消息到Broker确认提交或者回滚，Broker通过Request中的commitLogOffset，获取到上面状态为TransactionPreparedType的消息（简称消息A），然后重新构造一条与消息A内容相同的消息B，设置状态为TransactionCommitType或者TransactionRollbackType，然后保存。其中TransactionCommitType类型的，会放commitLogOffset到consumerQueue中，TransactionRollbackType类型的，消息体设置为空，不会放commitLogOffset到consumerQueue中.

Broker 链接复用吗?
同一个网络连接，客户端多个线程可以同时发送请求，应答响应通过 header 中的 opaque 字段来标识。

Broker 怎么处理超时连接?
如果某个连接超过特定时间没有活动(无读写事件)，则自动关闭此连接，并通知上层业务，清除连接对应的 注册信息。

Broker 和Name Server的心跳怎么实现的?
Broker启动时,会在定时线程池中每30秒注册信息至Name Server

  this.scheduledExecutorService.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                try {
                    BrokerController.this.registerBrokerAll(true, false);
                } catch (Throwable e) {
                    log.error("registerBrokerAll Exception", e);
                }
            }
        }, 1000 * 10, 1000 * 30, TimeUnit.MILLISECONDS);









集群配置 （http://blog.csdn.net/lovesomnus/article/details/51769977）

RocketMQ 网络部署特点

.Name Server是一个几乎无状态节点，可集群部署，节点之间无任何信息同步。

.Broker部署相对复杂，Broker 分为Master与Slave，一个Master 可以对应多个Slave，但是一个Slave只能对应一个Master，Master与Slave 的对应关系通过指定相同的BrokerName，不同的BrokerId来定 义，BrokerId为0 表示Master，非0 表示Slave。Master也可以部署多个。每个 Broker与 Name Server集群中的所有节点建立长连接，定时注册 Topic信息到所有 Name Server。

.Producer与Name Server集群中的其中一个节点（随机选择）建立长连接，定期从Name Server取Topic 路由信息，并向提供Topic服务的Master 建立长连接，且定时向Master发送心跳。Producer完全无 状态，可集群部署。

.Consumer与Name Server集群中的其中一个节点（随机选择）建立长连接，定期从Name Server 取Topic路由信息，并向提供Topic服务的Master、Slave建立长连接，且定时向Master、Slave发送心跳。Consumer既可以从Master订阅消息，也可以从Slave订阅消息，订阅规则由Broker 配置决定。

配置项
#所属集群名字
brokerClusterName=rocketmq-cluster
#broker名字，注意此处不同的配置文件填写的不一样
brokerName=broker-a|broker-b
#0 表示 Master，>0 表示 Slave
brokerId=0
#nameServer地址，分号分割
namesrvAddr=192.168.1.101:9876;192.168.1.102:9876
#在发送消息时，自动创建服务器不存在的topic，默认创建的队列数
defaultTopicQueueNums=4
#是否允许 Broker 自动创建Topic，建议线下开启，线上关闭
autoCreateTopicEnable=true
#是否允许 Broker 自动创建订阅组，建议线下开启，线上关闭
autoCreateSubscriptionGroup=true
#Broker 对外服务的监听端口
listenPort=10911
#删除文件时间点，默认凌晨 4点
deleteWhen=04
#文件保留时间，默认 48 小时
fileReservedTime=120
#commitLog每个文件的大小默认1G
mapedFileSizeCommitLog=1073741824
#ConsumeQueue每个文件默认存30W条，根据业务情况调整
mapedFileSizeConsumeQueue=300000
#destroyMapedFileIntervalForcibly=120000
#redeleteHangedFileInterval=120000
#检测物理文件磁盘空间
diskMaxUsedSpaceRatio=88
#存储路径
storePathRootDir=/usr/local/alibaba-rocketmq/store
#commitLog 存储路径
storePathCommitLog=/usr/local/alibaba-rocketmq/store/commitlog
#消费队列存储路径存储路径
storePathConsumeQueue=/usr/local/alibaba-rocketmq/store/consumequeue
#消息索引存储路径
storePathIndex=/usr/local/alibaba-rocketmq/store/index
#checkpoint 文件存储路径
storeCheckpoint=/usr/local/alibaba-rocketmq/store/checkpoint
#abort 文件存储路径
abortFile=/usr/local/alibaba-rocketmq/store/abort
#限制的消息大小
maxMessageSize=65536

#flushCommitLogLeastPages=4
#flushConsumeQueueLeastPages=2
#flushCommitLogThoroughInterval=10000
#flushConsumeQueueThoroughInterval=60000
#Broker 的角色
#- ASYNC_MASTER 异步复制Master
#- SYNC_MASTER 同步双写Master
#- SLAVE
brokerRole=ASYNC_MASTER

#刷盘方式
#- ASYNC_FLUSH 异步刷盘
#- SYNC_FLUSH 同步刷盘
flushDiskType=ASYNC_FLUSH
#checkTransactionMessageEnable=false
#发消息线程池数量
#sendMessageThreadPoolNums=128
#拉消息线程池数量
#pullMessageThreadPoolNums=128
