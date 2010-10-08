package com.rcpcompany.uibindings.extests.utils;

import static com.rcpcompany.uibindings.extests.BaseTestUtils.*;
import static org.junit.Assert.*;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.junit.Test;

import com.rcpcompany.uibindings.Constants;
import com.rcpcompany.uibindings.IValueBinding;
import com.rcpcompany.uibindings.extests.views.TestView;
import com.rcpcompany.uibindings.tests.shop.Contact;
import com.rcpcompany.uibindings.tests.shop.ShopFactory;
import com.rcpcompany.uibindings.utils.IFormCreator;
import com.rcpcompany.uibindings.utils.StringList;

/**
 * Tests of {@link StringList}.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public class StringListTests {
	@Test
	public void testABC() {
		final Contact contact = ShopFactory.eINSTANCE.createContact();
		final TestView testView = createTestView(this);
		final IFormCreator form = testView.createFormCreator(contact);

		final IValueBinding binding = form.addField("address");

		form.finish();

		final IObservableList list = binding.getArgument(Constants.ARG_VALID_VALUES, IObservableList.class, null);

		assertNotNull(list);

		assertEquals(3, list.size());

		assertEquals("a", list.get(0));
		assertEquals("b", list.get(1));
		assertEquals("c", list.get(2));
	}
}