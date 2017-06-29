package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FFMPEG获取视频时长、复制视频源、获取每一秒的截屏
 * @author dzw
 *
 */
public class FFMpegTest {

	static String prfix_ffmpeg = "D:\\ffmpeg\\bin\\ffmpeg";

	public static void main(String[] args) {
		int i = getVideoTime("D:\\ffmpeg\\bin\\aaa.mp4", prfix_ffmpeg);
		FFMpegTest.coverterVideo("D:\\ffmpeg\\bin\\cc745615e273d51a33ed5795afd79d01.mp4", "D:\\\\ffmpeg\\\\bin\\\\aaa.mp4",
				i);
	}

	public static void coverterVideo(String in, String out, Integer times) {

		StringBuilder commond = new StringBuilder();
		commond.append(prfix_ffmpeg);
		commond.append(" -i ");
		commond.append(in);
		commond.append(" -vcodec copy -acodec copy -f mp4 ");
		commond.append(out);

		System.out.println(commond.toString());
		Process pro = null;
		Process pro1 = null;
		try {
			if (!new File(out).exists()) {
				System.out.println("***********coverter begin*********");
				pro = Runtime.getRuntime().exec(commond.toString());
				System.out.println(
						"***********" + new SimpleDateFormat("yy-MM-dd HH:MM:ss").format(new Date()) + "***********");
				pro.waitFor();
				System.out.println(
						"***********" + new SimpleDateFormat("yy-MM-dd HH:MM:ss").format(new Date()) + "***********");
				System.out.println("**********coverter finished!!!*********");
			}
//			pro.destroy();

			if (new File(out).exists()) {
				System.out.println("*********************截图开始***********************");
				for (int i = 0; i < times; i++) {
					StringBuilder commond1 = new StringBuilder();
					commond1.append(prfix_ffmpeg);
					commond1.append(" -i ");
					commond1.append(out);
					commond1.append(" -y -f image2 -ss ");
					commond1.append(i);
					commond1.append(" -vframes 1 ");
					commond1.append("-s 350*240 ");
					commond1.append("D:\\ffmpeg\\bin\\imgs\\");
					commond1.append(i);
					commond1.append(".jpg");
					pro1 = Runtime.getRuntime().exec(commond1.toString());
					pro1.waitFor();
				}
				System.out.println("*********************截图结束***********************");
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				pro.getErrorStream().close();
				pro.destroy();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 获取视频总时间
	 * 
	 * @param viedo_path
	 *            视频路径
	 * @param ffmpeg_path
	 *            ffmpeg路径
	 * @return
	 */
	public static int getVideoTime(String video_path, String ffmpeg_path) {
		List<String> commands = new java.util.ArrayList<String>();
		commands.add(ffmpeg_path);
		commands.add("-i");
		commands.add(video_path);
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commands);
			final Process p = builder.start();

			// 从输入流中读取视频信息
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();

			// 从视频信息中解析时长
			String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
			Pattern pattern = Pattern.compile(regexDuration);
			Matcher m = pattern.matcher(sb.toString());
			if (m.find()) {
				int time = getTimelen(m.group(1));
				System.err
						.println(video_path + ",视频时长：" + time + ", 开始时间：" + m.group(2) + ",比特率：" + m.group(3) + "kb/s");
				return time;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	// 格式:"00:00:10.68"
	private static int getTimelen(String timelen) {
		int min = 0;
		String strs[] = timelen.split(":");
		if (strs[0].compareTo("0") > 0) {
			min += Integer.valueOf(strs[0]) * 60 * 60;// 秒
		}
		if (strs[1].compareTo("0") > 0) {
			min += Integer.valueOf(strs[1]) * 60;
		}
		if (strs[2].compareTo("0") > 0) {
			min += Math.round(Float.valueOf(strs[2]));
		}
		return min;
	}
}
