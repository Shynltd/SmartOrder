let bill = require('../model/bill');
let billOne = require('../model/menubillone');

// module.exports.getListMenu = async (req, res) => {
//     let listFood = await menu.find({type: "Food"});
//     let listDrink = await menu.find({type: "Drink"});
//     res.json({listFood, listDrink});
// }
module.exports.getListBillUnpaid = async (req, res) => {
    let findListUnpaid = await bill.find({status: "Chưa thanh toán"});
    if (findListUnpaid) {
        res.json(findListUnpaid);
    } else {
        res.status(500).json({message: 'Fail'});
    }
}
module.exports.getListBillPaid = async (req, res) => {
    let findListPaid = await bill.find({status: "Đã thanh toán"});
    if (findListPaid) {
        res.json(findListPaid);
    } else {
        res.status(500).json({message: 'Fail'});
    }
}
module.exports.postPaid = async (req, res) => {
    let {id} = req.params;
    let {nameCashier} = req.body;
    let paid = await bill.findOneAndUpdate({_id:id}, {
        $set: {
            nameCashier,
            status: "Đã thanh toán"
        },
    }, {new: true});
    if (paid) {
        res.status(200).json({message: 'Đã thanh toán'});
    } else {
        res.status(500).json({message: 'Fail'});
    }
}
module.exports.postOrder = async (req, res) => {
    let {tableCode} = req.body;
    let totalPrice = 0;
    let nameCashier = "null";
    let status = "Chưa thanh toán";
    let findBill = await bill.findOne({tableCode, status});
    if (findBill) {
        let billCode = findBill.billCode;
        let {list_menu} = req.body;
        for (let i = 0; i < list_menu.length; i++) {
            let {name} = list_menu[i];
            let findFoodOrder = await billOne.findOne({name, billCode});
            if (findFoodOrder) {
                let sl = findFoodOrder.sl + list_menu[i].sl;
                let totalMoney = findFoodOrder.price * sl;
                await billOne.findOneAndUpdate({_id: findFoodOrder._id}, {
                    $set: {
                        sl,
                        totalMoney,
                    },
                }, {new: true});
            } else {
                let {sl} = list_menu[i];
                let {image} = list_menu[i];
                let {price} = list_menu[i];
                let {type} = list_menu[i];
                let totalMoney = price * sl;
                let add = new billOne({billCode, image, sl, name, price, type, totalMoney});
                add.save();
            }
        }
        let d = await billOne.find({billCode});
        for (let i = 0; i < d.length; i++) {
            totalPrice += d[i].totalMoney;
        }
        await bill.findOneAndUpdate({billCode}, {
            $set: {
                totalPrice,
            },
        }, {new: true});
    } else {
        let currentdate = new Date();
        let list = await bill.find({});
        let listCount = list.length + 1;
        let billCode = "HD" + tableCode.toString() + currentdate.getDate() + (currentdate.getMonth() + 1) + currentdate.getFullYear() + listCount.toString();
        let {list_menu} = req.body;
        for (let i = 0; i < list_menu.length; i++) {
            let {image} = list_menu[i];
            let {sl} = list_menu[i];
            let {name} = list_menu[i];
            let {price} = list_menu[i];
            let {type} = list_menu[i];
            let totalMoney = price * sl;
            let add = new billOne({billCode, image, sl, name, price, type, totalMoney});
            add.save();
            totalPrice += totalMoney;
        }
        let addBill = new bill({billCode, nameCashier, tableCode, totalPrice, status});
        addBill.save();
    }
}

