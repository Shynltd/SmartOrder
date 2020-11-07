let bill = require('../model/bill');
let billone = require('../model/menubillone');
let menu = require('../model/menu');

module.exports.getAllBills = async (req, res) => {
    let allBills = await bill.find();
    res.render('bill/listBill', {allBills});
}
module.exports.getCreateBill = async (req, res) => {
    let allMenu = await menu.find();
    res.render('bill/createBill', {allMenu, err: false})
}
module.exports.postCreateBill = async (req, res) => {
    let test = req.body.name;
    if (Array.isArray(test)) {
        let amount = req.body.amount;
        for (let i = 0; i < test.length; i++) {
            let findMenu = await menu.findOne({_id: test[i]});
            if (findMenu) {
                let name = findMenu.name;
                let price = findMenu.price;
                let image = findMenu.image;
                let type = findMenu.type;
                for (let j = 0; j < amount.length; j++) {
                    if (amount[j] == "") {
                        amount.splice(j, 1);
                    }
                }
                console.log(amount)
                let totalMoney = price * amount[i];
                let addBillOne = await billone.create({name, price, image, type, amount: amount[i], totalMoney});
                if (addBillOne){
                    console.log(totalMoney);
                }
            }
        }
    } else {
        let findMenu = await menu.findOne({_id: test});
        if (findMenu) {
            let amount = req.body.amount;
            totalMoney = findMenu.price * amount[0];
        }
    }
}