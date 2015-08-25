package com.ushahidi.android.domain.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public final class TestFixtures {

    public static final Long DEPLOYMENT_ID = 1l;

    public static final Long ID = 2l;

    private static Form mForm;

    public static Form getForm() {
        if (mForm == null) {
            mForm = new Form();
            mForm._id = ID;
            mForm.setCreated(new java.util.Date(1439502954));
            mForm.setDescription("A Basic Form");
            mForm.setDeploymentId(DEPLOYMENT_ID);
            mForm.setName("Basic form");
            mForm.setUpdated(new java.util.Date(1439503014));
            mForm.setDisabled(true);
        }
        return mForm;
    }

    public static List<FormAttribute> getFormAttributes() {
        List<FormAttribute> formAttributeList = new ArrayList<>();
        formAttributeList.add(getFormAttribute());
        return formAttributeList;
    }

    public static FormAttribute getFormAttribute() {
        FormAttribute formAttribute = new FormAttribute();
        formAttribute._id = ID;
        formAttribute.setKey("test_varchar");
        formAttribute.setLabel("Test varchar");
        formAttribute.setInput(FormAttribute.Input.TEXT);
        formAttribute.setType(FormAttribute.Type.VARCHAR);
        formAttribute.setRequired(true);
        formAttribute.setOptions(null);
        formAttribute.setPriority(1);
        formAttribute.setCardinality(1);
        formAttribute.setDeploymentId(DEPLOYMENT_ID);
        formAttribute.setFormId(2l);
        return formAttribute;
    }
}
