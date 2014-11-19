var net = require('net');

net.createServer(function (socket) {
    socket.on('data', function (data) {
        var message = data.toString('utf8');
        message = decryptSymetricMessage(message);
        message = removePadding(message);
        var packet = JSON.parse(message);
        // TODO: stuff with packet
    });
    socket.on('end', function (data) {
        console.log('User ended connection.');
    });
    socket.on('close', function (data) {
        console.log('User closed connection');
    });
}).listen(8000, '0.0.0.0');

/*
   Removes null padding characters from string
   Ensures that we get a string that can be parsed from JSON
*/
function removePadding (message) {
    var re = /[\0]/g;
    return message.replace(re, "");
}

// TODO: Implement AES decryption.
function decryptSymetricMessage (message) {
    return message;
}

// TODO: Implement AES encryption.
function encryptSymetricMessage (message) {
    return message;
}

/*
Encryption code for AES
crypto = require "crypto"
var iv = new Buffer('asdfasdfasdfasdf')
var key = new Buffer('asdfasdfasdfasdfasdfasdfasdfasdf')
var cipher = crypto.createCipheriv('aes-256-cbc', key, iv);
cipher.update(new Buffer("mystring"));
var enc = cipher.final('base64');
*/
