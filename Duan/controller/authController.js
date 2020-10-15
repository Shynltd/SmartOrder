var user = require('../model/user')
module.exports.getLogin =  (req, res) => {
    res.render('login', {error:false});
}
module.exports.checkLogin = async (req, res) => {
    var email = req.body.email;
    var userId = await user.findOne({userName : email });
    if (userId){
        var password = req.body.password;
        if (userId.passWord == password){
            if (userId.role == 'Admin'){
                res.cookie('id',userId._id,{
                    signed:true
                });
                res.redirect('/users');
            } else {
                res.cookie('id',userId._id, {
                    signed: true
                });
                res.redirect('users/info/'+userId._id);
            }

        } else {
            res.render('login', {error:true, msg:'Wrong Password'});
        }
    } else {
        res.render('login', {error:true, msg:'Account does not exist'});
    }

}
module.exports.logOut = (req, res) => {
    if (req.signedCookies.id){
        res.clearCookie('id');
    }
    res.redirect('/');
}