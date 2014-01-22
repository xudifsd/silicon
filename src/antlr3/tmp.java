		// no lebels
		if (method.labelList.size() == 0) {
			for (ast.stm.T stm : method.statements) {
				while (debugIndex < method.debugList.size()
						&& this.position == debugValue) {
					this.printSpace();
					this.sayln(currentDebug.toString());
					debugIndex++;
					if (debugIndex < method.debugList.size()) {
						currentDebug = method.debugList.get(debugIndex);
						debugValue = Integer.parseInt(currentDebug.addr);
					}
				}
				this.printSpace();
				stm.accept(this);
				this.sayln("");
			}
		}
		// labels exist
		 else {
			for (ast.stm.T stm : method.statements) {
				//special labels
				if (labelIndex < method.labelList.size()
						&& (currentLab.lab.startsWith("sswitch_data")
								|| currentLab.lab.startsWith("pswitch_data") || currentLab.lab
									.startsWith("array_"))
						&& (stm instanceof ast.stm.Instruction.PackedSwitchDirective
								|| stm instanceof ast.stm.Instruction.SparseSwitchDirective || stm instanceof ast.stm.Instruction.ArrayDataDirective)) {
					this.printSpace();
					this.sayln(currentLab.toString());
					labelIndex++;
					if (labelIndex < method.labelList.size()) {
						currentLab = method.labelList.get(labelIndex);
						labelValue = Integer.parseInt(currentLab.add);
					}
				}
				// normal labels  && position == labelValue
				 else {
					while (debugIndex < method.debugList.size()
							&& this.position == debugValue) {
						this.printSpace();
						this.sayln(currentDebug.toString());
						debugIndex++;
						if (debugIndex < method.debugList.size()) {
							currentDebug = method.debugList.get(debugIndex);
							debugValue = Integer.parseInt(currentDebug.addr);
						}
					}
					while (labelIndex < method.labelList.size()
							&& this.position == labelValue) {
						this.printSpace();
						this.sayln(currentLab.toString());
						if (currentLab.lab.startsWith("try_end")) {
							this.catchList = new ArrayList<String>();
							while (catchIndex < method.catchList.size()
									&& this.position == catchValue) {
								//this.printSpace();
								//this.sayln(currentCatch.toString());
								this.catchList.add(currentCatch.toString());

								catchIndex++;
								if (catchIndex < method.catchList.size()) {
									currentCatch = method.catchList
											.get(catchIndex);
									catchValue = Integer
											.parseInt(currentCatch.add);
								}
							}
							this.printCatchList();
						}
						labelIndex++;
						if (labelIndex < method.labelList.size()) {
							currentLab = method.labelList.get(labelIndex);
							labelValue = Integer.parseInt(currentLab.add);
						}
					}

				}
				this.printSpace();
				stm.accept(this);
				this.sayln("");
			}
		}

		/* (source)
		 * current version     |         prev version(b25fa6c)
		 *---------------------------------------------------
		 * instruction         |         instruction
		 * :try_end_3          |         .end method
		 * .catch....          |
		 * .end method         |
		 */
		while (labelIndex < method.labelList.size()
				&& this.position == labelValue) {
			this.printSpace();
			this.sayln(currentLab.toString());
			if (currentLab.lab.startsWith("try_end")) {
				this.catchList = new ArrayList<String>();
				while (catchIndex < method.catchList.size()
						&& this.position == catchValue) {
					//this.printSpace();
					//this.sayln(currentCatch.toString());
					this.catchList.add(currentCatch.toString());
					catchIndex++;
					if (catchIndex < method.catchList.size()) {
						currentCatch = method.catchList.get(catchIndex);
						catchValue = Integer.parseInt(currentCatch.add);
					}
				}
				this.printCatchList();
			}
			labelIndex++;
			if (labelIndex < method.labelList.size()) {
				currentLab = method.labelList.get(labelIndex);
				labelValue = Integer.parseInt(currentLab.add);
			}
		}