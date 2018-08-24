##帮助
redis-cli --help

##连接制定ip 和host
redis-cli -h localhost -p 7000

##重复发ping请求
redis-cli -h localhost -p 7000 -r 3  ping  重复发送三次ping

redis-cli -h localhost -p 7000 -r 3 -i 2  ping  重复发送三次每隔2秒ping

##内存使用信息
redis-cli -h localhost -p 7000 -r 3 -i 2 info | grep used_memory_human