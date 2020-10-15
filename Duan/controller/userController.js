var user = require('../model/user');
let fs = require('fs');
let uniqid = require("uniqid");

module.exports.getUser = async (req, res) => {
    var findAllUser = await user.find();
    res.render('user/listUser', {users: findAllUser});
}
module.exports.getUserInfo = async (req, res) => {
    var id = req.params.id;
    var idCookie = req.signedCookies.id;
    var idAdmin = await user.findOne({_id: idCookie});
    var userIf = await user.findOne({_id: id});
    if (id == idCookie) {
        if (userIf) {
            res.render('user/infoUser', {userIf, err:false})
        }
    } else if (id != idCookie && idAdmin.role == 'Admin') {
        res.render('user/infoUser', {userIf, err:false});
    } else {
        res.redirect('/users/info/' + idCookie)
    }
}
module.exports.deleteUser = async (req, res) => {
    var idUser = req.params.id;
    var checkAdmin = await user.findOne({_id: idUser});
    if (checkAdmin.role == 'Admin') {
        res.redirect('/users');
        res.alert('Không được xóa admin');
        return;
    }
    user.findOneAndRemove({_id:idUser}).catch(err => {
        res.send('Lỗi')
    });

      res.redirect('/users');
}
module.exports.updateUser = async (req, res) => {
        let userId = req.params.id;
        var findUser = await user.findById(userId);
        if (findUser) {
            var name = req.body.fullname;
            var phone = req.body.phone;
            var avatar = findUser.avatar;
            var age = req.body.age;
            var address = req.body.address;
            var cmnd = req.body.cmnd;
            var role = req.body.role;
            if (req.files){
                fs.unlinkSync(`./uploads/${findUser.avatar}`);
                avatar = req.files.avatar;
                let filename ="/user/"+ uniqid()+ "-" +avatar.name;
                avatar.mv(`./uploads/${filename}`)
                avatar = filename;
            }
            var password = req.body.password;
            var password2 = req.body.password2;
            if (password == password2 && findUser.passWord == password){
                let updated = await user.findOneAndUpdate({_id: userId}, {
                    fullName:name,
                    phone,
                    avatar,
                    age,
                    address,
                    role,
                    soCMND:cmnd
                })
                if (updated){
                    res.redirect('/users/info/'+findUser._id);
                } else {
                    res.error(404);
                }
            }
        }
        res.redirect('/users/info/'+findUser._id);
}
module.exports.changePassword = async (req, res) => {
    var userId = req.params.id;
    var findUser = await user.findById(userId);
    if (findUser){
        var password = req.body.currentpass;
        if (password == findUser.passWord){
            var newPassword = req.body.newpass;
            var confirmPassword = req.body.confirmpass;
            if (newPassword == confirmPassword){
                let updated = await user.findOneAndUpdate({_id: userId}, {
                    passWord: newPassword,
                })
                if (updated){
                    res.redirect('/users/info/'+findUser._id);
                } else {
                    res.error(404);
                }
            }
        } else {
            res.render('user/infoUser', {userIf:findUser,
                err: true,
                msg: 'Mật khẩu cũ không chính xác'
            });
        }
    }
}
module.exports.test = (req, res) => {

}
