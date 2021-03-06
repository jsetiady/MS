import java.util.Scanner;

import com.model.user.User;
import com.view.meeting.MeetingView;
import com.view.user.UserView;

/**
 * @author jessiesetiady
 *
 */
public class MainMeetingScheduler {
	
	private static boolean exit = false;
	
	public static void showHelp(int role) {
		switch(role) {
			case 1: 
				System.out.println("Administrator command");
				System.out.println("---------------------");
				System.out.println("list-user");
				System.out.println("detail-user <email>");
				System.out.println("add-user");
				System.out.println("edit-user <email>");
				System.out.println("del-user <email>");
				System.out.println();
			case 2:
			case 3:
				System.out.println("Initiator command");
				System.out.println("---------------------");
				System.out.println("create-meeting");
				System.out.println("list-meeting");
				System.out.println("detail-meeting <meeting-id>");
				System.out.println("edit-meeting <meeting-id>");
				System.out.println("cancel-meeting <meeting-id>");
				System.out.println("run scheduler <meeting-id>");
				System.out.println();
				System.out.println("Participant command");
				System.out.println("---------------------");
				System.out.println("list-invitation");
				System.out.println("detail-invitation <meeting-id>");
				System.out.println("accept-invitation <meeting-id>");
				System.out.println("reject-invitation <meeting-id>");
				break;
		}
	}
	
	public static boolean checkCommandRole(int expectedRole, int actualRole) {
		if(actualRole <=  expectedRole) {
			return true;
		}
		return false;
	}
	
	public static void showErrorPrivilegeCommand() {
		System.out.println("You do not have privilege to execute this command");
	}
		
	public static void processMenu(String command, String email, int role) {
		MeetingView mv = new MeetingView();
		UserView uv = new UserView();
		String[] cmd = command.split(" ");
		boolean test;
		switch(cmd[0]) {
			case "list-user":
				if (checkCommandRole(1, role))
					uv.showListUser();
				else
					showErrorPrivilegeCommand();
				break;
			case "add-user":
				if (checkCommandRole(1, role))
					uv.createUser();
				else
					showErrorPrivilegeCommand();
				break;
			case "detail-user":
				if (checkCommandRole(1, role)) {
					uv.viewUserByEmail(cmd[1]);
				}
				else
					showErrorPrivilegeCommand();
				break;
				
			case "edit-user": 
				if (checkCommandRole(1, role)) {
					uv.editUser(cmd[1]);
				} else 
					showErrorPrivilegeCommand();
				break;
			case "del-user": 
				if (checkCommandRole(1, role)) {
					uv.deleteUser(cmd[1]);
				} else {
					showErrorPrivilegeCommand();
				}
				break;
			
			case "create-meeting" :
				if (checkCommandRole(2, role))
					mv.createMeetingView(email);
				else
					showErrorPrivilegeCommand();
				break;
			case "list-meeting" : 
				if (checkCommandRole(2, role))
					mv.displayCreatedMeeting(email);
				else
					showErrorPrivilegeCommand();
				break;
			case "detail-meeting" :
				if (checkCommandRole(2, role)) {
					try {
						mv.viewMeetingByID(cmd[1]);
					} catch(Exception e) {
						System.out.println("Invalid command. Format: detail-meeting <meeting-id>");
					}
				}
				else
					showErrorPrivilegeCommand();
				break;
			case "edit-meeting" : 
				if(checkCommandRole(2, role)) {
					try {
						mv.editMeeting(cmd[1], email);
					} catch(Exception e) {
						System.out.println("Invalid command. Format: edit-meeting <meeting-id>");
						e.printStackTrace();
					}
				}
				else
					showErrorPrivilegeCommand();
				break;
			case "cancel-meeting" : 
				if(checkCommandRole(2, role)) {
					try {
						mv.cancelMeeting(cmd[1], email);
					} catch(Exception e) {
						System.out.println("Invalid command. Format: cancel-meeting <meeting-id>");
					}
				}
				else
					showErrorPrivilegeCommand();
				break;
			case "run-scheduler" : 
				if(checkCommandRole(2, role)) {
					try {
						mv.runScheduler(cmd[1], email);
					} catch(Exception e) {
						System.out.println("Invalid command. Format: run-meeting <meeting-id>");
					}
				}
				else
					showErrorPrivilegeCommand();
				break;
			
			case "list-invitation" : 
				if (checkCommandRole(3, role))
					mv.viewMeetingInvitation(email);
				else
					showErrorPrivilegeCommand();
				break;
			case "detail-invitation" :
				if (checkCommandRole(3, role)) {
					try {
						mv.detailInvitation(cmd[1], email);
					} catch(ArrayIndexOutOfBoundsException e) {
						System.out.println("Invalid command. Format: detail-invitation <meeting-id>");
					}
				}
				else
					showErrorPrivilegeCommand();
				break;
			case "accept-invitation" :
				if (checkCommandRole(3, role)) {
					try {
						mv.acceptInvitation(cmd[1], email);
					} catch(ArrayIndexOutOfBoundsException e) {
						System.out.println("Invalid command. Format: accept-invitation <meeting-id>");
					}
				}
				else
					showErrorPrivilegeCommand();
				break;
			case "reject-invitation" : 
				if (checkCommandRole(3, role)) {
					try {
						mv.rejectInvitation(cmd[1], email);
					} catch(Exception e) {
						System.out.println("Invalid command. Format: reject-invitation <meeting-id>");
					}
				}
				else
					showErrorPrivilegeCommand();
				break;
			
			case "help" : showHelp(role); break;
			case "logout" : break;
			case "exit" : System.exit(0); break;
			case "" :  break;
			
			default: System.out.println("Unrecognized command option"); break;
		}
	}
	
	public static String roleToString(int role) {
		switch(role) {
			case 1: return "Administrator";
			case 2: return "Meeting initiator or participant";
			case 3: return "Meeting participant";
		}
		return "";
	}
	
	public static void main(String args[]) {
		Scanner s = new Scanner(System.in);
		UserView uv = new UserView();
		User user;
		String email, password, command;
		int role;
		boolean login = false;
		
		do {
			System.out.println("\n+-------------------------------------------+");
			System.out.println("|LOGIN                                      |");
			System.out.println("+-------------------------------------------+");
			do {
				System.out.print(" Email      : "); email = s.nextLine();
				System.out.print(" Password   : "); password = s.nextLine();
				System.out.println();
				user = uv.login(email, password);
				if(user!=null) {
					login = true;
				} else {
					System.out.println("## wrong email or password ##");
				}
			} while(!login);
			
			login = false;
			do {
					if(user.isAdmin()) {
						login = true;
						role = 1;
					} else {
						login = true;
						role = 2;
					}
				if(login) {
					System.out.println("\nYou have signed in as a " + roleToString(role) + ".");
					System.out.println("Waiting for your command...\n");	
				}
			} while(!login);
			do {
				System.out.print("> "); command = s.nextLine();
				processMenu(command, email, role);
			} while(!command.equals("logout"));
		} while(!command.equals("exit"));
	}
	
}
