package edu.usc.irds.sparkler.config;


import edu.usc.irds.sparkler.SparklerException;
import edu.usc.irds.sparkler.plugin.FetcherJBrowser;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class FetcherJBrowserProps implements BaseConfig {
    /******************************
     * FETCHER JBROWSER PROPTERTIES
     *****************************/
    @NotNull(message = "fetcherJbrowser.socketTimeout cannot be null")
    @Min(value = 1, message = "fetcherJbrowser.socketTimeout cannot be less that 1")
    private int socketTimeout;
    @NotNull(message = "fetcherJbrowser.connectTimeout cannot be null")
    @Min(value = 1, message = "fetcherJbrowser.connectTimeout cannot be less that 1")
    private int connectTimeout;

    /*********************
     * GETTERS AND SETTERS
     ********************/
    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    //TODO: add these functionality to PluginProps
    /****************************************************************************
     * @param sparklerConfig Object from which FetcherJBrowserProps needs to be
     *                       extracted
     * @return FetcherJBrowserProps Object
     * @throws SparklerException If Keys are not valid or JBrowser is not enabled
     ***************************************************************************/
    public static FetcherJBrowserProps getFetcherJBrowserProps(SparklerConfig sparklerConfig) throws SparklerException {
        if (!isFetcherJBrowserActive(sparklerConfig)) {
            throw new SparklerException("fetcherJbrowser is not in the list of active plugins");
        }
        return sparklerConfig.getPluginProps(FetcherJBrowser.PLUGIN_ID, FetcherJBrowserProps.class);
    }

    /******************************************************
     * @return true if FetcherJBrowserProps is a valid bean
     * @throws SparklerException
     *****************************************************/
    public Boolean validateFetcherJBrowserProps() throws SparklerException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<FetcherJBrowserProps>> constraintViolations = validator.validate(this);
        for (ConstraintViolation constraintViolation : constraintViolations) {
            throw new SparklerException(constraintViolation.getMessage());
        }
        return true;
    }

    /****************************************************************************
     * @param sparklerConfig config object from which active plugins list will be
     *                       extracted
     * @return true if fetcherJbrowser is active
     ***************************************************************************/
    public static Boolean isFetcherJBrowserActive(SparklerConfig sparklerConfig) {
        return sparklerConfig.getActivePlugins().contains(FetcherJBrowser.PLUGIN_ID);
    }

}
