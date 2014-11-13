var net = require('net');

net.createServer(function (socket) {
    console.log('A user connected.');
    socket.write('Welcome, user!\r\n');
    socket.on('data', function (data) {
        console.log('got "data"', data);
    });
    socket.on('end', function (data) {
        console.log('Shucks. A user ended the connection.');
    });
    socket.on('close', function () {
        console.log('Uh oh. Looks like the user disconnected.');
    });
}).listen(8000);
