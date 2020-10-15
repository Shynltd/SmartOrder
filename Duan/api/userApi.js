var user = require('../model/user');
module.exports.checkLogin = async (req, res) => {
    var email = req.body.email;
    var userId = await user.findOne({userName: email});
    if (userId) {
        var password = req.body.password;
        if (userId.passWord == password) {
            res.json({
                status:'OK',
                message:'Logged in successfully',
                user: userId,
            });
        } else {
            res.json({status:'Fail',message: 'Wrong Password'});
        }
    } else {
        res.json({status:'Fail', message: 'Account does not exist'});
    }
}
