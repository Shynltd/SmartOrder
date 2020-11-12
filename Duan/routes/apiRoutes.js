let express = require('express');
let router = express.Router();
let authApi = require('../api/authApi');
let authMiddle = require('../middleware/authMiddle');
let userApi = require('../api/userApi');
let menuApi = require('../api/menuApi');
let tableApi = require('../api/tableApi');


router.post('/login', authApi.checkLogin);

router.get('/user', authMiddle.verifyToken, userApi.getAllUser);
router.get('/user/:id',  userApi.getUserInfo);
router.post('/user/create',authMiddle.verifyToken, userApi.postCreateUser);
router.delete('/user/delete/:id', userApi.deleteUser);
router.put('/user/update/:id', userApi.updateUser);

router.get('/menu', authMiddle.verifyToken, menuApi.getListMenu);
router.post('/menu/create',authMiddle.verifyToken, menuApi.postCreate);
router.delete('/menu/delete/:id', menuApi.deleteMenu);
router.put('/menu/update/:id', menuApi.postUpdate);

router.get('/table', authMiddle.verifyToken, tableApi.getListTable);
router.post('/table/create', tableApi.postCreate);
router.delete('/table/delete/:id', tableApi.deleteTable);
router.put('/table/update/:id', tableApi.postUpdate);


module.exports = router;