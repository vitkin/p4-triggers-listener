#!/bin/bash

export P4PORT=1666
export P4USER=perforce
export P4PASSWD=perforce
export P4_TRIGGERS_LISTENER_URL=http[s]://SERVER[:PORT]/p4-triggers-listener


echo "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>
<change-commit>
    <client>${1}</client>
    <clienthost>${2}</clienthost>
    <clientip>${3}</clientip>
    <serverhost>${4}</serverhost>
    <serverip>${5}</serverip>
    <serverport>${6}</serverport>
    <serverroot>${7}</serverroot>
    <user>${8}</user>
    <changelist>${9}</changelist>
    <oldchangelist>${10}</oldchangelist>
    <changeroot>${11}</changeroot>
    <description><![CDATA[`p4 describe ${9}`]]></description>
</change-commit>" | \
curl -s -H "Content-Type: application/xml" \
     -X POST \
     --data-binary "@-" \
     ${P4_TRIGGERS_LISTENER_URL}/rest/notifyChangeCommit

exit 0
