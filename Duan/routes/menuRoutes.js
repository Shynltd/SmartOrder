var express = require('express');
var router = express.Router();
var authMiddle = require('../middleware/authMiddle')
var menuController = require('../controller/menuController');

router.get('/',authMiddle.reqAuth, authMiddle.checkAdmin, menuController.getListMenu);
router.get('/create',authMiddle.reqAuth, authMiddle.checkAdmin, menuController.getCreate);

router.post('/create',authMiddle.reqAuth, authMiddle.checkAdmin, menuController.postCreate);

module.exports = router;
