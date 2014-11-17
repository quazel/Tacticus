var net = require('net');

net.createServer(function (socket) {
    socket.on('data', function (data) {
        console.log('burgle');
    });
}).listen(8000, '0.0.0.0');

/* Encryption code for AES
crypto = require "crypto"
var iv = new Buffer('asdfasdfasdfasdf')
var key = new Buffer('asdfasdfasdfasdfasdfasdfasdfasdf')
var cipher = crypto.createCipheriv('aes-256-cbc', key, iv);
cipher.update(new Buffer("mystring"));
var enc = cipher.final('base64');
*/
