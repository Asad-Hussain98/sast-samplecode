const express = require('express');

const app = express();

app.get('/health_check', (req, res) => {
    // res.header("Access-Control-Allow-Origin", "*");
    res.send(JSON.stringify({ message: 'I\'m alive', status: 200 }));
    console.log(JSON.stringify({ message: 'I\'m alive', status: 200 }))
});

app.listen(5000);
