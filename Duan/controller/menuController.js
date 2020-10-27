let uniqid = require("uniqid");
let menu = require('../model/menu');

module.exports.getListMenu = async (req, res) => {
    var listFood = await menu.find({type: "Food"});
    var listDrink = await menu.find({type: "Drink"});
    res.render('menu/listMenu', {listFood, listDrink});
}
module.exports.getCreate = (req, res) => {
    res.render('menu/createMenu', {err: false})
}
module.exports.postCreate = async (req, res) => {
    var name = req.body.name;
    var type = req.body.type;
    var price = req.body.price;
    var amount = null;
    if (type == "Drink") {
        amount = req.body.amount;
    }
    var image = null;
    if (req.files) {
        image = req.files.image;
        let filename = "/menus/" + uniqid() + "-" + image.name;
        image.mv(`./uploads/${filename}`)
        image = filename;
    } else {
        res.render('user/createMenu', {err: true, msg: "Bạn chưa upload ảnh"});
    }
        var addMenu = await menu.create({name, price, image, type, amount}).catch(err =>{
            res.code(404);
        });
        if (addMenu) {
            res.redirect('/menus');
        }
}