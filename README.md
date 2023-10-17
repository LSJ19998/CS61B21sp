# CS61B-21SP

there are some problems about push operation of git :

1. "slove failed to connect to github.con port xxxx : Time out" : 

git config --global http.proxy

git config --global --unset http.proxy

2. "slove OpenSSL SSL_read: Connection was aborted, errno 10053" : 

git config --global http.postBuffer 524288000


3. fatal: unable to access 'https://github.com/LSJ19998/CS61B-21SP.git/': OpenSSL SSL_read: Connection was reset, errno 10054

git config --global http.sslVerify "false"
重启git
