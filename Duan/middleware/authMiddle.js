var user = require('../model/user');

module.exports.checkAdmin = async (req, res, next) => {
    var findAdmin = await user.findOne({_id: req.signedCookies.id});
    if (findAdmin){
        if (findAdmin.role == "Admin"){
            next();
            return;
        } else {
            res.redirect('/users/info/'+req.signedCookies.id);
            return;
        }
    } else {
        res.redirect('/users/info/'+req.signedCookies.id);
        return;
    }
}

module.exports.reqAuth = async (req, res, next) => {
     if (req.signedCookies.id){
         var findUser = await user.findOne({_id: req.signedCookies.id});
         if (findUser){
             res.locals.findUser = findUser;
             res.locals.role = findUser.role;
             next();

             return;
         } else {
             res.redirect('/');
             return;
         }
     } else {
         res.redirect('/');
         return;
     }
}