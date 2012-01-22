
package com.robmcguinness.stateless;

import static com.robmcguinness.stateless.StatelessEncoder.mergeParameters;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public abstract class StatelessAjaxEventBehavior extends AjaxEventBehavior {

    public StatelessAjaxEventBehavior(final String event) {
        super(event);
    }

    /**
     * Adding parameters the generated URL.
     *
     *
     * @see AbstractAjaxBehavior#getCallbackUrl(boolean)
     */
    @Override
    public CharSequence getCallbackUrl() {
        final Url url = Url.parse(super.getCallbackUrl().toString());
        final PageParameters params = getPageParameters();

        return mergeParameters(url, params).toString();
    }

    protected abstract PageParameters getPageParameters();

    /**
     * @return always {@literal true}
     */
    @Override
    public boolean getStatelessHint(final Component component) {
        return true;
    }
}