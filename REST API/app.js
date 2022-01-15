var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

var asiakasRouter = require('./routes/asiakas');
var tiliRouter = require('./routes/tili');
var tilitapahtumatRouter = require('./routes/tilitapahtumat');

var korttiRouter = require('./routes/kortti');
var loginRouter = require('./routes/login');


var app = express();

const basicAuth = require('express-basic-auth');
app.use(basicAuth({users: { 'automaatti1': '1234' }}))

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/asiakas', asiakasRouter);
app.use('/tili', tiliRouter);
app.use('/tilitapahtumat', tilitapahtumatRouter);

app.use('/kortti', korttiRouter);
app.use('/login', loginRouter);


module.exports = app;
