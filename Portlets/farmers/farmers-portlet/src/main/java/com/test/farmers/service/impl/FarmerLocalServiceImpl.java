package com.test.farmers.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.test.farmers.FarmerCreateException;
import com.test.farmers.model.Farmer;
import com.test.farmers.service.base.FarmerLocalServiceBaseImpl;

import javax.portlet.ActionRequest;
import java.text.DateFormat;
import java.util.Date;

/**
 * The implementation of the farmer local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.test.farmers.service.FarmerLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.test.farmers.service.base.FarmerLocalServiceBaseImpl
 * @see com.test.farmers.service.FarmerLocalServiceUtil
 */
public class FarmerLocalServiceImpl extends FarmerLocalServiceBaseImpl {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never reference this interface directly. Always use {@link com.test.farmers.service.FarmerLocalServiceUtil} to access the farmer local service.
     */

    public Farmer changeFarmerArchiveStatus(long farmerId) throws SystemException, PortalException{
        Farmer farmer = getFarmer(farmerId);
        farmer.setIsArchive(!farmer.getIsArchive());
        farmerPersistence.update(farmer);
        return farmer;
    }

    public Farmer updateFarmerData(Farmer farmer) throws SystemException, PortalException {
        validate(farmer);
        farmerPersistence.update(farmer);
        return farmer;
    }

    protected void validate(Farmer farmer) throws FarmerCreateException {
        if (Validator.isNull(farmer.getName())) {
            throw new FarmerCreateException("There is an Error in adding Farmer: farmer name is empty");
        }
        if (Validator.isNull(farmer.getInn())) {
            throw new FarmerCreateException("There is an Error in adding Farmer: farmer inn is empty");
        }
    }
}
