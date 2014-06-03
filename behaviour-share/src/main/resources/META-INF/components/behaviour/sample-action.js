(function() {
    YAHOO.Bubbling.fire("registerAction",
    {
        actionName: "onActionSample",
        fn: function ixxus_onActionSample(file) {
            this.modules.actions.genericAction(
            {
                success:
                {
                    message: this.msg("message.sample.success", file.displayName, Alfresco.constants.USERNAME)
                },
                failure:
                {
                    message: this.msg("message.sample.failure", file.displayName, Alfresco.constants.USERNAME)
                },
                webscript:
                {
                    name: "ixxus/sampleWebScript?nodeRef={nodeRef}&userName={userName}",
                    stem: Alfresco.constants.PROXY_URI,
                    method: Alfresco.util.Ajax.GET,
                    params:
                    {
                        nodeRef: file.nodeRef,
                        userName: Alfresco.constants.USERNAME
                    }
                },
                config:
                {
                }

            });
        }
    });
})();