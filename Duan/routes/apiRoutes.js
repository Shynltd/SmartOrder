let express = require('express');
let router = express.Router();
let apiController = require('../api/userApi');

router.post('/login',apiController.checkLogin);;

module.exports = router;