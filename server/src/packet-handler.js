var packets = require('./packets');

module.exports = {

    handlePacket0KeepAlive: function (socket, packet) {
        var responsePacket = packets.Packet0KeepAlive();
        var b = new Buffer(4);
        var responseBody = JSON.stringify(responsePacket);
        b.writeInt32BE(responseBody.length, 0);
        socket.write(b);
        socket.write(responseBody, 'utf8');
    },

    handlePacket1Login: function (socket, packet) {

    },

    handlePacket2UpdateStatus: function (socket, packet) {

    },

    handlePacket3FetchFriends: function (socket, packet) {

    },

    handlePacket4AddScheduleItem: function (socket, packet) {

    },

    handlePacket5UpdateScheduleItem: function (socket, packet) {

    },

    handlePacket6RegisterAccount : function (socket, packet) {

    }

};
