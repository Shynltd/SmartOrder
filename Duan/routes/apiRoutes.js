let express = require('express');
let router = express.Router();
let apiController = require('../api/userApi');

router.get('/login',apiController.checkLogin);;

module.exports = router;