let table = require('../model/table');

module.exports.getListTable = async (req, res) => {
    let keyword = req.query.keyword === undefined ? "" : req.query.keyword;
    let listTable = await table.find();
    var page = parseInt(req.query.page) || 1;
    var perPage = 8;
    var start = (page - 1) * perPage;
    var end = page * perPage;
    res.render('table/listTable', {listTable: listTable.slice(start,end)});
}
module.exports.getCreate = async (req, res) => {

    let findAllTables = await table.find();
    res.render('table/createTables', {err: false, findAllTables});
}

module.exports.postCreate = async (req, res) => {
    let tableCode = req.body.tableCode;
    let tableSeats = req.body.tableSeats;
    if (tableCode.length === 0) {

    } else if (tableSeats.length === 0) {

    } else {
        let addTable = await table.create({tableCode, tableSeats});
        if (addTable) {
            res.redirect('/tables');
        }
    }
}
module.exports.getUpdate = async (req, res) => {
    let findTable = await table.findById(req.params.id);
    if (findTable) {
        res.render('table/updateTable', {findTable, err: false});
    }

}

module.exports.postUpdate = async (req, res) => {
    let findTable = await table.findById(req.params.id);
    if (findTable) {
        let updateTable = await table.findOneAndUpdate({_id: req.params.id}, {
            tableCode: req.body.tableCode,
            tableSeats: req.body.tableSeats,
        })
        if (updateTable){
            res.redirect('/tables');
        } else {
            res.render('table/updateTable', {findTable, err: true, msg: `Lỗi`});
        }
    }


}
module.exports.deleteTable = async (req, res) => {
    let findTable = await table.findById(req.params.id);
    if (findTable) {
        let deleteMenu = await table.findOneAndRemove({_id: req.params.id});
        if (deleteMenu) {
            res.redirect('/tables');
        }
    } else {
        res.status(404).message('ID not found');
    }
}