var express = require('express');
var routes = express.Router();
var authMiddle = require('../middleware/authMiddle')
var menuController = require('../controller/menuController');

routes.get('/',authMiddle.reqAuth, authMiddle.checkAdmin, menuController.getListMenu);
routes.get('/create',authMiddle.reqAuth, authMiddle.checkAdmin, menuController.getCreate);

routes.post('/create',authMiddle.reqAuth, authMiddle.checkAdmin, menuController.postCreate);

module.exports = routes;
