module.exports = {

    Packet0KeepAlive: function () {
        return {
            packet_type: 0,
            timestamp: Math.floor(new Date() / 1000),
            contents: "Pong!"
        };
    },

    Packet1Login: function () {
        return {
             packet_type: 1,
             timestamp: Math.floor(new Date() / 1000),
             contents: {
                 token: "blurp"
             }
         };
    },

    Packet2UpdateStatus: function (new_status) {
        return {
            packet_type: 2,
            timestamp: Math.floor(new Date() / 1000),
            contents: {
                status: new_status
            }
        };
    },

    Packet3FetchFriends: function () {
        return {
            packet_type: 3,
            timestamp: Math.floor(new Date() / 1000),
            contents: {

            }
        };
    },

    Packet4AddScheduleItem: function () {
        return {
            packet_type: 4,
            timestamp: Math.floor(new Date() / 1000),
            contents: {

            }
        };
    },

    Packet5UpdateScheduleItem: function () {
        return {
            packet_type: 5,
            timestamp: Math.floor(new Date() / 1000),
            contents: {

            }
        };
    },

    Packet6RegisterAccount: function () {
        return {
            packet_type: 5,
            timestamp: Math.floor(new Date() / 1000),
            contents: {

            }
        };
    }

};
