// For an empty local demo database only.
db = db.getSiblingDB('moremall_demo');
db.admin.updateOne({name: 'admin'}, {$setOnInsert: {name: 'admin', password: 'local-demo-change-me', email: 'admin@example.com', mobile: '', avatar: ''}}, {upsert: true});
