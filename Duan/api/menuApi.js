let menu = require('../model/menu');

module.exports.getListMenu = async (req, res) => {
    let listFood = await menu.find({type: "Food"});
    let listDrink = await menu.find({type: "Drink"});
    res.json({listFood, listDrink});
}
module.exports.postCreate = async (req, res) => {
    let name = req.body.name;
    let type = req.body.type;
    let price = req.body.price;
    let amount = null;
    if (type == "Drink") {
        amount = req.body.amount;
    }
    let image = null;
    const menus = new menu({name, type, price, amount, image});
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
        let updated = await menu.findOneAndUpdate({_id: req.params.id}, {
            $set: {
                name: req.body.name,
                type: req.body.type,
                amount: req.body.amount,
                price: req.body.price,
                image: null,
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
