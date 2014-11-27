var packets = require('./packets');

module.exports = {

    handlePacket0KeepAlive : function (socket, packet) {
        var responsePacket = packets.Packet0KeepAlive();
        var b = new Buffer(4);
        var responseBody = JSON.stringify(responsePacket);
        b.writeInt32BE(responseBody.length, 0);
        socket.write(b);
        socket.write(responseBody, 'utf8');
    }

};
