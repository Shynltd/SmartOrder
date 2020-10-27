let uniqid = require("uniqid");
let table = require('../model/table');

module.exports.getListMenu = async (req, res) => {
    var listTable = await table.find();
    res.render('table/listTable', {listTable});
}
module.exports.getCreate = (req, res) => {
    res.render('able/listTable', {err: false});
}

module.exports.postCreate = async (req, res) => {
    var number = req.body.number;
    var seats = req.body.seats;
    if (number.length === 0) {

    } else if (seats.length === 0) {

    } else {
        var addTable = await table.create({name, seats}).catch(err =>{
            res.code(404);
        });
        if (addTable) {
            res.redirect('/tables');
        }
    }
}