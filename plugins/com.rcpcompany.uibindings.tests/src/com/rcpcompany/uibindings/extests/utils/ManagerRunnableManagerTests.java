package com.rcpcompany.uibindings.extests.utils;

import static com.rcpcompany.uibindings.extests.BaseTestUtils.*;
import static org.junit.Assert.*;

import org.eclipse.swt.widgets.Display;
import org.junit.Before;
import org.junit.Test;

import com.rcpcompany.uibindings.IManager;
import com.rcpcompany.uibindings.utils.IManagerRunnable;
import com.rcpcompany.uibindings.utils.IManagerRunnableManager;

/**
 * Tests of {@link IManagerRunnableManager} and friends.
 * 
 * @author Tonny Madsen, The RCP Company
 */
public class ManagerRunnableManagerTests {
	@Before
	public void before() {
		resetAll();
	}

	/**
	 * Tests that there are exactly one manager
	 */
	@Test
	public void onlyOneManagerTest() {
		final IManager mng = IManager.Factory.getManager();

		IManagerRunnableManager service = mng.getService(IManagerRunnableManager.class);
		assertEquals(null, service);

		service = IManagerRunnableManager.Factory.getManager();
		assertNotNull(service);

		final IManagerRunnableManager service2 = IManagerRunnableManager.Factory.getManager();
		assertEquals(service, service2);
	}

	public String state = "";

	/**
	 * Tests that the added runnables are executed
	 */
	@Test
	public void executedTest() {
		final IManagerRunnableManager mng = IManagerRunnableManager.Factory.getManager();
		assertTrue(mng.isEmpty());

		IManagerRunnable.Factory.asyncExec("run", this, new Runnable() {
			@Override
			public void run() {
				state += "1";
			}
		});
		assertFalse(mng.isEmpty());
		yield();

		assertTrue(mng.isEmpty());
		assertEquals("1", state);
	}

	/**
	 * Tests that canceled runables are not executed
	 */
	@Test
	public void cancelTest() {
		final IManagerRunnableManager mng = IManagerRunnableManager.Factory.getManager();
		assertTrue(mng.isEmpty());

		IManagerRunnable.Factory.asyncExec("run", this, new Runnable() {
			@Override
			public void run() {
				state += "1";
			}
		});
		assertFalse(mng.isEmpty());

		IManagerRunnable.Factory.cancelAsyncExec("run", this);
		assertTrue(mng.isEmpty());
		yield();

		assertTrue(mng.isEmpty());
		assertEquals("", state);
	}

	/**
	 * Tests that replacing runables works
	 */
	@Test
	public void replaceTest() {
		final IManagerRunnableManager mng = IManagerRunnableManager.Factory.getManager();
		assertTrue(mng.isEmpty());

		IManagerRunnable.Factory.asyncExec("run", this, new Runnable() {
			@Override
			public void run() {
				state += "1";
			}
		});
		assertFalse(mng.isEmpty());

		IManagerRunnable.Factory.asyncExec("run", this, new Runnable() {
			@Override
			public void run() {
				state += "3";
			}
		});
		assertFalse(mng.isEmpty());
		yield();

		assertTrue(mng.isEmpty());
		assertEquals("3", state);
	}

	/**
	 * Tests that replacing runables works with type null
	 */
	@Test
	public void replaceNullTest() {
		final IManagerRunnableManager mng = IManagerRunnableManager.Factory.getManager();
		assertTrue(mng.isEmpty());

		IManagerRunnable.Factory.asyncExec(null, this, new Runnable() {
			@Override
			public void run() {
				state += "1";
			}
		});
		assertFalse(mng.isEmpty());

		IManagerRunnable.Factory.asyncExec(null, this, new Runnable() {
			@Override
			public void run() {
				state += "2";
			}
		});
		assertFalse(mng.isEmpty());
		yield();

		assertTrue(mng.isEmpty());
		assertEquals("12", state);
	}

	/**
	 * Tests that the runnables are executed in bunches
	 */
	@Test
	public void timedTest() {
		final IManagerRunnableManager mng = IManagerRunnableManager.Factory.getManager();
		assertTrue(mng.isEmpty());

		IManagerRunnable.Factory.asyncExec(null, this, new Runnable() {
			@Override
			public void run() {
				state += "1";
				try {
					Thread.sleep(30);
				} catch (final InterruptedException ex) {
					fail(ex.getMessage());
				}
			}
		});
		assertFalse(mng.isEmpty());

		IManagerRunnable.Factory.asyncExec(null, this, new Runnable() {
			@Override
			public void run() {
				state += "2";
				try {
					Thread.sleep(30);
				} catch (final InterruptedException ex) {
					fail(ex.getMessage());
				}
			}
		});
		assertFalse(mng.isEmpty());

		IManagerRunnable.Factory.asyncExec(null, this, new Runnable() {
			@Override
			public void run() {
				state += "3";
				try {
					Thread.sleep(30);
				} catch (final InterruptedException ex) {
					fail(ex.getMessage());
				}
			}
		});
		assertFalse(mng.isEmpty());

		Display.getCurrent().asyncExec(new Runnable() {
			@Override
			public void run() {
				state += "A";
			}
		});
		assertFalse(mng.isEmpty());
		yield();

		assertTrue(mng.isEmpty());
		assertEquals("12A3", state);
	}
}