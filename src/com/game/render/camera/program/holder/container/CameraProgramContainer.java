package com.game.render.camera.program.holder.container;

import com.game.render.camera.program.CameraProgram;
import com.game.render.camera.program.CameraProgramOwner;
import com.game.render.camera.program.holder.CameraProgramHolder;
import com.game.render.camera.program.program.AbsCharacterProgram;
import com.game.utils.arrContainer.EscapyNamedArray;
import net.henryco.struct.Structurized;
import net.henryco.struct.container.tree.StructNode;
import net.henryco.struct.container.tree.StructTree;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Henry on 11/12/16.
 */
public class CameraProgramContainer implements Structurized {

	public final float DEF_WIDTH;
	public final float DEF_HEIGHT;
	public final CameraProgramHolder cameraProgramHolder;
	public final EscapyNamedArray<Integer> idProgramArray = new EscapyNamedArray<>(Integer.class);
	private CameraProgramOwner defaultCameraProgramOwner;

	public CameraProgramContainer(float w, float h, CameraProgramHolder camHolder, CameraProgramOwner defaultCamOwner) {
		DEF_WIDTH = w;
		DEF_HEIGHT = h;
		cameraProgramHolder = camHolder;
		defaultCameraProgramOwner = defaultCamOwner;
	}

	public int addProgram(String name, CameraProgram<float[]> program) {
		return (int) idProgramArray.addSource(cameraProgramHolder.addCameraProgram(checkForHolder(program)), name).getLast();
	}
	public int addProgram(CameraProgram<float[]> program) {
		return (int) idProgramArray.addSource(cameraProgramHolder.addCameraProgram(checkForHolder(program))).getLast();
	}

	private CameraProgram<float[]> checkForHolder(CameraProgram<float[]> program) {
		if (program instanceof AbsCharacterProgram)
			if (((AbsCharacterProgram) program).getOwner() == null)
				((AbsCharacterProgram) program).setOwner(defaultCameraProgramOwner);
		return program;
	}

	public CameraProgramContainer load(String file) {
		return loadFromStruct(new StructTree(file).mainNode.getStructSafe("Camera"));
	}

	@Override
	public CameraProgramContainer loadFromStruct(StructNode structNode) {

		String[] nameOpt = new String[]{"name", "Name", "id", "ID"};
		String[] consOpt = new String[]{"url", "URL", "link", "class"};
		System.out.println(structNode);
		structNode.getStructSafe("Programs");
		for (StructNode program : structNode.getStructArray()) {
			for (StructNode pr : program.getStructArray()) {
				for (StructNode p : pr.getStructArray()) {
					List<Class> listType = new ArrayList<>();
					List<Object> listOb = new ArrayList<>();
					StructNode[] args = p.getStructArray();
					for (StructNode arg : args) {
						if (!arg.name.equalsIgnoreCase("function") && !arg.name.equalsIgnoreCase("def")) {
							Object[] arr = arg.loadInstancedField(this);
							listType.add((Class) arr[0]);
							listOb.add(arr[1]);
						}
					}
					String cosntructorUrl = p.getString("error", consOpt);
					CameraProgram<float[]> newFloatProgram = instanceConstructor(cosntructorUrl, listType.toArray(new Class[0]), listOb.toArray(new Object[0]));
					addProgram(p.getString("", nameOpt), newFloatProgram);
					p.invokeFunctions(newFloatProgram, this);

				}
			}
		}
		return this;
	}



	@SuppressWarnings("unchecked")
	private static <T> T instanceConstructor(String name, Class[] classArr, Object[] objectArr) {
		try {
			Constructor constructor = ClassLoader.getSystemClassLoader().loadClass(name).getDeclaredConstructor(classArr);
			constructor.setAccessible(true);
			return (T) constructor.newInstance(objectArr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final String defaultStructFile =
			"#import struct\n" +
					"#sugar StdProgram \"com.game.render.camera.program.program.stdProgram.StdCameraProgram\"\n" +
					"#sugar ProgExec \"com.game.render.camera.program.program.stdProgram.programExecutor.ProgramExecutor\"\n" +
					"#sugar FollowCam \"com.game.render.camera.program.program.stdProgram.programExecutor.factory.ProgramExecutors\"\n" +
					"\n" +
					"\n" +
					"Var {\n" +
					"\tDEF_SRC_W: {\n" +
					"\t\ttype: float\n" +
					"\t\tval.java.instanced(\"DEF_WIDTH\")\n" +
					"\t}\n" +
					"\tDEF_SRC_H: {\n" +
					"\t\ttype: float\n" +
					"\t\tval.java.instanced(\"DEF_HEIGHT\")\n" +
					"\t}\n" +
					"\tX_PROG: { \n" +
					"\t\ttype: ProgExec\n" +
					"\t\tval.java.global(FollowCam, \"followCam\")\n" +
					"\t}\n" +
					"\n" +
					"}\n" +
					"\n" +
					"Camera {\n" +
					"\n" +
					"\tPrograms[0]: {\n" +
					"\n" +
					"\t\t*fw = Var::DEF_SRC_W\n" +
					"\t\t*fh = Var::DEF_SRC_H\n" +
					"\t\t*prog = Var::X_PROG\n" +
					"\n" +
					"\t\tStdCameraProgram(&fw, &fh, [float, 0.35], [float, 0.35]);\n" +
					"\t\tStdCameraProgram --> function setXProgram(&prog);\n" +
					"\t\tStdCameraProgram {\n" +
					"\t\t\tfunction {\n" +
					"\t\t\t\tsetCorrection([float, 120], [float, 0]);\n" +
					"\t\t\t\tsetMinTranslations([float, 0.4], [float, 0.4]);\n" +
					"\t\t\t}\n" +
					"\t\t\tname: \"default\";\n" +
					"\t\t\tclass StdProgram\n" +
					"\t\t}\t\t\n" +
					"\n" +
					"\t}\n" +
					"}";
}
