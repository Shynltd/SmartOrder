let table = require('../model/table');

module.exports.getListTable = async (req, res) => {
    let listTable = await table.find();
    res.json(listTable);
}

module.exports.postCreate = async (req, res) => {
    let tableCode = req.body.tableCode;
    let tableSeats = req.body.tableSeats;
    const addTable = new table({tableCode, tableSeats});
    addTable.save((err) => {
        if (err) {
            res.status(500).json({
                message: `Error is ${err}`
            });
        } else {
            res.status(200).json({
                message: `Add new table successfully`
            });
        }
    });
}

module.exports.postUpdate = async (req, res) => {
    let findTable = await table.findById(req.params.id);
    if (findTable) {
        let updated = await table.findOneAndUpdate({_id: req.params.id}, {
            $set: {
                tableCode: req.body.tableCode,
                tableSeats: req.body.tableSeats,
            },
        }, {new: true});
        if (updated) {
            res.status(200).json({message: 'Table Updated'})
        } else {
            res.status(500).json({message: 'Update Table Fail'})
        }
    } else {
        res.status(500).json({message: 'Table does not exist'})
    }
}
module.exports.deleteTable = async (req, res) => {
    let findTable = await table.findById(req.params.id);
    if (findTable) {
        const deleteTable = await table.findOneAndRemove({_id: req.params.id});
        if (deleteTable) {
            res.status(200).json({message: 'Table Deleted'})
        } else {
            res.status(500).json({message: 'Delete Table Fail'})
        }
    } else {
        res.status(500).json({message: 'Table does not exist'})
    }
}