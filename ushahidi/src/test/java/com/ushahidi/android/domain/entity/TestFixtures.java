package com.ushahidi.android.domain.entity;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public final class TestFixtures {

    private static Form mForm;

    public static Form getForm() {
        if (mForm == null) {
            mForm = new Form();
            mForm._id = 1l;
            mForm.setCreated(new java.util.Date(1439502954));
            mForm.setDescription("A Basic Form");
            mForm.setDeploymentId(1l);
            mForm.setName("Basic form");
            mForm.setUpdated(new java.util.Date(1439503014));
            mForm.setAttributes(getFormAttribute());
            mForm.setDisabled(true);
        }
        return mForm;
    }

    public static FormAttribute getFormAttribute() {
        FormAttribute formAttribute = new FormAttribute();
        formAttribute.setKey("test_varchar");
        formAttribute.setLabel("Test varchar");
        formAttribute.setInput(FormAttribute.Input.TEXT);
        formAttribute.setType(FormAttribute.Type.VARCHAR);
        formAttribute.setRequired(true);
        formAttribute.setOptions(null);
        formAttribute.setPriority(1);
        return formAttribute;
    }
}
