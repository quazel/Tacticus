module.exports = {

    Packet0KeepAlive: function () {
        return {type: 1, timestamp: Math.floor(new Date() / 1000), contents: "Pong!"};
    }

};
