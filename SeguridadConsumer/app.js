const express = require('express');
const logger = require('morgan');
const soap = require('soap');
const app = express();
const http = require('http');

app.use(express.urlencoded({ extended: false }));
app.use(express.json());

var url = 'http://localhost:4000/database?wsdl';

app.get('/:email/:password', (req, res) => {
  soap.createClient(url, (err, client) => {
    console.log(err);
    if (err) console.log(err);
    if (!err) {

        var args = { arg0: req.params.email, arg1: req.params.password };

      client.add(args, (err, result) => {
          console.log(err)
          console.log(result)
        if (result.return === 0) res.send({ mensaje: 'Error' });
        if (result.return === 1) res.send({ mensaje: 'Autenticado' });
      });
    }
  });
});

app.get('/test', (req, res) => {
  soap.createClient(url, (err, client) => {
    if (err) console.log(err);
    if (!err) {
      client.testConnection((err, result) => {
        if (!err) {
          console.log(result);
          res.send(result);
        }
      });
    }
  });
});

let port = 5000;
app.listen(port);

console.log(`App start on port ${port} `);
