let express = require('express');
let router = express.Router();
let authApi = require('../api/authApi');
let authMiddle = require('../middleware/authMiddle');
let userApi = require('../api/userApi');
let menuApi = require('../api/menuApi');


router.post('/login', authApi.checkLogin);

router.get('/user', authMiddle.testToken, userApi.getAllUser);
router.get('/user/:id',  userApi.getUserInfo);
router.post('/user/create', userApi.postCreateUser);
router.delete('/user/delete/:id', userApi.deleteUser);
router.put('/user/update/:id', userApi.updateUser);

router.get('/menu',  menuApi.getListMenu);
router.get('/menu/:id',  menuApi.getInfoMenu);
router.post('/menu/create', menuApi.postCreate);
router.delete('/menu/delete/:id', menuApi.deleteMenu);
router.put('/menu/update/:id', menuApi.postUpdate);


module.exports = router;