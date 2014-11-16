var net = require('net');
var rsa = require('node-rsa');

net.createServer(function (socket) {
    console.log('A user connected.');
    socket.write('Welcome, user!\n');
    socket.on('data', function (data) {
        var message = data.toString("utf8");
        console.log('got "message"', message);
        var packet = JSON.parse(message);
        console.log(packet.packet_type);
    });
    socket.on('end', function (data) {
        console.log('Shucks. A user ended the connection.');
    });
    socket.on('close', function () {
        console.log('Uh oh. Looks like the user disconnected.');
    });
}).listen(8000, "0.0.0.0");

/* Encryption code for AES
crypto = require "crypto"
var iv = new Buffer('asdfasdfasdfasdf')
var key = new Buffer('asdfasdfasdfasdfasdfasdfasdfasdf')
var cipher = crypto.createCipheriv('aes-256-cbc', key, iv);
cipher.update(new Buffer("mystring"));
var enc = cipher.final('base64');
*/
