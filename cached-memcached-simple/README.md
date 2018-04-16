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