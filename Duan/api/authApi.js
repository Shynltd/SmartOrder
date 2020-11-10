let user = require('../model/user');
let jwt = require('jsonwebtoken');

module.exports.checkLogin = async (req, res) => {
    let phone = req.body.phone;
    let findUser = await user.findOne({phone: phone});
    if (findUser) {
        let password = req.body.password;
        if (findUser.passWord === password) {
            const token = jwt.sign({id: findUser._id}, 'duan', {algorithm: 'HS256', expiresIn:60*60*24});
            res.json({
                status: 'OK',
                token,
                role: findUser.role,
                name: findUser.fullName,
                avatar: findUser.avatar,
                message: 'Logged in successfully'
            });
        } else {
            res.json({status: 'Fail', message: 'Wrong Password'});
        }
    } else {
        res.json({status: 'Fail', message: 'Account does not exist'});
    }
}
