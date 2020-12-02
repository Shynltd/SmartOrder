let bill = require('../model/bill');
let billone = require('../model/menubillone');
let menu = require('../model/menu');


module.exports.getAllBills = async (req, res) => {

    let Bills = await bill.find();
    console.log(Bills);
    res.render('bill/listBill', {bills: Bills});

}

module.exports.getBills = async (req, res) => {
    let id = req.params.billCode;
    let bills = await bill.findOne({billCode: id}).catch((err) => {
    });
    let billones = await billone.find({billCode: id});
    res.render('bill/detail', {bills, billones});
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