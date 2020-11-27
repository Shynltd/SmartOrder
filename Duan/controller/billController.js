let bill = require('../model/bill');
let billone = require('../model/menubillone');
let menu = require('../model/menu');

module.exports.getAllBills = async (req, res) => {
    let allBills = await billone.find();
    console.log(allBills);
}
module.exports.getCreateBill = async (req, res) => {
    let allMenu = await menu.find();
    res.render('bill/createBill', {allMenu, err: false})
}
module.exports.postCreateBill = async (req, res) => {
    let addMenu = await bill.create({nameCashier:"Sằm Thanh Hiếu", tableCode:5, totalPrice:100000, status:"Chưa thanh toán"}).catch(err => {
        res.send(err.message);
    });
    if (addMenu){
        res.send(`Thêm thành công`);
    }
}