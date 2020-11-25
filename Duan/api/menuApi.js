let menu = require('../model/menu');
let uniqid = require('uniqid');

module.exports.getListMenu = async (req, res) => {
    let listFood = await menu.find({type: "Food"});
    let listDrink = await menu.find({type: "Drink"});
    res.json({listFood, listDrink});
}
module.exports.getListMenuAll = async (req, res) => {
    let findMenu = await menu.find({});
    if (findMenu){
    res.json(findMenu);
    } else {
        res.status(500).json({message: 'Get Menu Fail'});
    }
}
module.exports.postCreate = async (req, res) => {
    let name = req.body.name.substring(1, req.body.name.length - 1);
    let type = req.body.type.substring(1, req.body.type.length - 1);
    let price = req.body.price;
    let amount = null;
    if (type == "Drink") {
        amount = req.body.amount;
    }
    let image = null;
    if (req.files) {
        let avatarName = "/menus/" + uniqid() + "-" + req.files.avatar.name;
        req.files.avatar.mv(`./uploads${avatarName}`);
        image = avatarName;
    }

    const menus = new menu({name, type, price, amount, image});
    console.log(menus);
    menus.save((err) => {
        if (err) {
            res.status(500).json({
                message: `Error is ${err}`
            });
        } else {
            res.status(200).json({
                message: `Add new food successfully`
            });
        }
    });
}
module.exports.postUpdate = async (req, res) => {
    let findFood = await menu.findById(req.params.id);
    if (findFood) {
        let image = findFood.image;
        if (req.files){
            let avatarName = "/menus/" + uniqid() + "-" + req.files.avatar.name;
            req.files.avatar.mv(`./uploads${avatarName}`);
            image = avatarName;
        }
        let updated = await menu.findOneAndUpdate({_id: req.params.id}, {
            $set: {
                name: req.body.name.substring(1, req.body.name.length - 1),
                type: req.body.type.substring(1, req.body.type.length - 1),
                amount: req.body.amount,
                price: req.body.price,
                image: image,

            },
        }, {new: true});
        if (updated) {
            res.status(200).json({message: 'Food Updated'})
        } else {
            res.status(500).json({message: 'Update Food Fail'})
        }
    } else {
        res.status(500).json({message: 'Food does not exist'})
    }
}
module.exports.deleteMenu = async (req, res) => {
    let findFood = await menu.findById(req.params.id);
    if (findFood) {
        const deleteMenu = await menu.findOneAndRemove({_id: req.params.id});
        if (deleteMenu) {
            res.status(200).json({message: 'Food Deleted'})
        } else {
            res.status(500).json({message: 'Delete Food Fail'})
        }
    } else {
        res.status(500).json({message: 'Food does not exist'})
    }
}
