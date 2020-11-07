var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var lodash = require('lodash');
var fileUpload = require('express-fileupload');

var authRoutes = require('./routes/authRoutes');
var userRoutes = require('./routes/userRoutes');
var menuRoutes = require('./routes/menuRoutes');
var tableRoutes = require('./routes/tableRoutes');
var apiRoutes = require('./routes/apiRoutes');
var billRoutes = require('./routes/billRoutes');
var mongoose = require('./monggo/monggosv');
var authMiddle = require('./middleware/authMiddle');

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser('sjdkahsdfs'));
app.use(express.static(path.join(__dirname, 'public')));
app.use(express.static(path.join(__dirname, 'uploads')));
app.use(fileUpload({
  createParentPath: true
}));


app.use('/', authRoutes);
app.use('/users',userRoutes);
app.use('/api', apiRoutes);
app.use('/menus', menuRoutes);
app.use('/tables', tableRoutes);
app.use('/bills', billRoutes);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
