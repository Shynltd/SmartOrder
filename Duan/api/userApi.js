let user = require('../model/user');

module.exports.getAllUser = async (req, res) => {
    let findUser = await user.find({});
    if (findUser) {
        res.json(findUser)
    } else {
        res.status(404).json({message: 'Get User Fail'});
    }
}
module.exports.getUserInfo = async (req, res) => {
    let userIf = await user.findById(req.params.id);
    if (!userIf) {
        res.status(500).json({message: 'User does not exist'})
    }
    res.status(200).json(userIf)

}
module.exports.deleteUser = async (req, res) => {
    let findUser = await user.findById(req.params.id);
    if (findUser) {
        const deleteUser = await user.findByIdAndDelete({_id: req.params.id});
        if (deleteUser) {
            res.status(200).json({message: 'User Deleted'})
        } else {
            res.status(500).json({message: 'Delete User Fail'})
        }
    } else {
        res.status(500).json({message: 'User does not exist'})
    }
}
module.exports.updateUser = async (req, res) => {
    let findUser = await user.findById(req.params.id);
    if (findUser) {
        let updated = await user.findOneAndUpdate({_id: req.params.id}, {
            $set: {
                fullName: req.body.fullName,
                phone: req.body.phone,
                age: req.body.age,
                address: req.body.address,
                role: req.body.role,
                indentityCardNumber: req.body.cmnd
            },
        }, {new: true});
        if (updated) {
            res.status(200).json({message: 'User Updated'})
        } else {
            res.status(500).json({message: 'Update User Fail'})
        }

    } else {
        res.status(500).json({message: 'User does not exist'})
    }

}
module.exports.changePassword = async (req, res) => {
    let findUser = await user.findById(req.params.id);
    if (findUser) {
        let password = req.body.currentpass;
        if (password === findUser.passWord) {
            let newPassword = req.body.newpass;
            let confirmPassword = req.body.confirmpass;
            if (newPassword === confirmPassword) {
                const updated = await user.findOneAndUpdate({_id: req.params.id}, {
                    passWord: newPassword,
                })
                if (updated) {
                    res.status(200).json({message: 'Password Updated'})
                } else {
                    res.status(500).json({message: 'Update Password Fail'})
                }
            } else {
                res.status(500).json({message: 'Confirm password is incorrect'})
            }
        } else {
            res.status(500).json({message: 'Wrong password'})
        }
    } else {
        res.status(500).json({message: 'User does not exist'})
    }
}
module.exports.postCreateUser = async (req, res) => {
    let phone = req.body.phone;
    let checkPhone = await user.findOne({phone: phone});
    if (checkPhone) {
        res.json({status: 'Fail', message: 'User already exists'});
    } else {
        let passWord = "123456";
        let role = req.body.role;
        let fullName = req.body.fullName;
        let indentityCardNumber = req.body.soCMND;
        let address = req.body.address;
        let age = req.body.age;
        let avatar = null
        const users = new user({passWord, role, fullName, indentityCardNumber, phone, address, age, avatar});
        users.save((err) => {
            if (err) {
                res.status(500).json({message: `Error is ${err}`});
            } else {
                res.status(200).json({
                    data: users,
                    message: `Register new user successfully`,
                });
            }
        });
    }
}