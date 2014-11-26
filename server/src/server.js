var net = require('net');

net.createServer(function (socket) {
    socket.on('data', function (data) {
        var message = data.toString('utf8');
        message = decryptSymetricMessage(message);
        message = removePadding(message);
        var packet = JSON.parse(message);
        switch (packet.packet_type) {
            case 0:
                console.log('Hello client!');
                var responsePacket = Packet0KeepAlive();
                var b = new Buffer(4);
                var responseBody = JSON.stringify(responsePacket);
                b.writeInt32BE(responseBody.length, 0);
                socket.write(b);
                socket.write(responseBody, 'utf8');
                break;
            default:
                console.log("wut");
                break;
        }
    });
    socket.on('end', function (data) {
        console.log('User ended connection.');
    });
    socket.on('close', function (data) {
        console.log('User closed connection');
    });
}).listen(8000, '0.0.0.0');


function Packet0KeepAlive() {
    return {type: 1, timestamp: Math.floor(new Date() / 1000), contents: "Pong!"};
}

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
