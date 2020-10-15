var express = require('express');
var routes = express.Router();
var authController = require('../controller/authController');
var userController = require('../controller/userController');

/* GET home page. */
routes.get('/', authController.getLogin);
routes.post('/auth', authController.checkLogin);
routes.get('/logout', authController.logOut);

module.exports = routes;
