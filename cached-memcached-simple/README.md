ehcache :

sample:


C10K:http://www.cocoachina.com/bbs/read.php?tid-1705273.html

https://www.oschina.net/translate/c10k

http://www.52im.net/thread-566-1-1.html

安装 

1.安装libevent库
yum install libevent libevent-deve      

2.yum install memcached

3.运行：
Memcached命令的运行：

$  /usr/bin/memcached  -h                           命令帮助
（1）作为前台程序运行：
从终端输入以下命令，启动memcached:

/usr/local/memcached/bin/memcached -p 11211 -m 64m -vv
（2）作为后台服务程序运行：
# /usr/local/memcached/bin/memcached -p 11211 -m 64m -d
或者
/usr/local/memcached/bin/memcached -d -m 64M -u root -l 192.168.0.200 -p 11211 -c 256 -P /tmp/memcached.pid



#术语

Page：分配给slab的内存空间，默认为1M。分配给slab后根据大小切分成chunk。

Chunk:用于缓存记录的内存空间。

Slab class：特定大小的chunk组；


#特点：
1. 协议简单

2.基于libevent事件处理

3.内置内存存储方式（slab）

4.节点之间互不通信。


slab allocator与传统 malloc，free的区别。

#问题：

##1.无法备份，重起无法恢复；（通过持久化解决，memcacheDB）


##2.无法查询（例如无法按key范围查询）

##3.没有提供内置的安全机制

##单点故障failover。



#典型问题解析：

(1)容量问题
(2)服务高可用
(3)扩展问题

##过期机制。
1 lazy expiration。get时查看时间戳。看是否过期。

2.LRU.最近最少使用。

##hash算法。

##热点问题。

##缓存与数据库的更新问题。

##别把缓存当存储。

##命名空间（通过对key做规范，避免key冲突而导致value被覆盖）。


##CAS。