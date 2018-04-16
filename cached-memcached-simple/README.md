ehcache :

sample:


C10K:http://www.cocoachina.com/bbs/read.php?tid-1705273.html

https://www.oschina.net/translate/c10k

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

