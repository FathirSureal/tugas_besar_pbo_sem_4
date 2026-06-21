-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 21, 2026 at 05:54 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `neuyomi`
--

-- --------------------------------------------------------

--
-- Table structure for table `contents`
--

CREATE TABLE `contents` (
  `id` varchar(500) NOT NULL,
  `judul` varchar(255) NOT NULL,
  `content_type` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `downloaded`
--

CREATE TABLE `downloaded` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `manga_id` varchar(100) DEFAULT NULL,
  `judul` varchar(255) DEFAULT NULL,
  `cover` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `downloaded`
--

INSERT INTO `downloaded` (`id`, `user_id`, `manga_id`, `judul`, `cover`, `created_at`) VALUES
(1, 14, 'https://myanimelist.net/manga/7/Hajime_no_Ippo', 'Hajime no Ippo', 'https://cdn.myanimelist.net/images/manga/2/250313l.jpg', '2026-06-20 09:52:35');

-- --------------------------------------------------------

--
-- Table structure for table `genres`
--

CREATE TABLE `genres` (
  `id` int(11) NOT NULL,
  `nama_genre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `genres`
--

INSERT INTO `genres` (`id`, `nama_genre`) VALUES
(1, 'Action'),
(2, 'Adventure'),
(34, 'Anti-Hero'),
(3, 'Comedy'),
(26, 'Cultivation'),
(4, 'Drama'),
(32, 'Dungeon'),
(37, 'Dystopian'),
(15, 'Ecchi'),
(5, 'Fantasy'),
(31, 'Gate'),
(16, 'Harem'),
(41, 'Historical'),
(8, 'Horror'),
(30, 'Hunter'),
(24, 'Isekai'),
(23, 'Josei'),
(40, 'Magic'),
(25, 'Martial Arts'),
(17, 'Mecha'),
(18, 'Music'),
(9, 'Mystery'),
(36, 'Post-Apocalyptic'),
(10, 'Psychological'),
(28, 'Regression'),
(6, 'Romance'),
(19, 'School'),
(11, 'Sci-Fi'),
(20, 'Seinen'),
(22, 'Shoujo'),
(21, 'Shounen'),
(7, 'Slice of Life'),
(38, 'Space Opera'),
(14, 'Sports'),
(33, 'Superhero'),
(12, 'Supernatural'),
(27, 'System'),
(13, 'Thriller'),
(39, 'Time Travel'),
(29, 'Tower'),
(35, 'Villain');

-- --------------------------------------------------------

--
-- Table structure for table `komik`
--

CREATE TABLE `komik` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `lang` varchar(10) NOT NULL DEFAULT 'en',
  `type` varchar(20) NOT NULL DEFAULT 'manga',
  `version` varchar(50) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `jenis` varchar(50) DEFAULT NULL,
  `publisher` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `komik`
--

INSERT INTO `komik` (`id`, `name`, `lang`, `type`, `version`, `url`, `jenis`, `publisher`) VALUES
(5, 'TakoManga', 'id', 'manhwa', '1.0.0', 'https://takomanga.com', NULL, NULL),
(6, 'MangaToon', 'en', 'manhua', '1.0.0', 'https://mangatoon.mobi', NULL, NULL),
(7, 'QiManga', 'en', 'manhua', '1.0.0', 'https://qimanga.com', NULL, NULL),
(10, 'ComiXology', 'en', 'comics', '1.0.0', 'https://www.comixology.com', NULL, NULL),
(11, 'MangaDex', 'id', 'manga', '1.0.0', 'https://mangadex.org', NULL, NULL),
(12, 'Komiku', 'id', 'manga', '1.0.0', 'https://komiku.id', NULL, NULL),
(13, 'MangaPlus', 'en', 'manga', '1.0.0', 'https://mangaplus.shueisha.co.jp', NULL, NULL),
(14, 'BATO.TO', 'en', 'manhwa', '1.0.0', 'https://bato.to', NULL, NULL),
(15, 'Webtoon', 'id', 'webtoon', '1.0.0', 'https://www.webtoons.com', NULL, NULL),
(16, 'Marvel Unlimited', 'en', 'comics', '1.0.0', 'https://www.marvel.com/unlimited', NULL, NULL),
(17, 'DC Universe', 'en', 'comics', '1.0.0', 'https://www.dc.com', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `komik_backup`
--

CREATE TABLE `komik_backup` (
  `id` int(11) NOT NULL DEFAULT 0,
  `name` varchar(255) NOT NULL,
  `lang` varchar(10) NOT NULL DEFAULT 'en',
  `type` varchar(20) NOT NULL DEFAULT 'manga',
  `version` varchar(50) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `komik_backup`
--

INSERT INTO `komik_backup` (`id`, `name`, `lang`, `type`, `version`, `url`) VALUES
(1, 'MangaDex', 'en', 'manga', '1.0.0', 'https://mangadex.org'),
(2, 'Komiku', 'id', 'manga', '1.0.0', 'https://komiku.id'),
(3, 'MangaPlus', 'en', 'manga', '1.0.0', 'https://mangaplus.shueisha.co.jp'),
(4, 'Webtoon', 'en', 'manhwa', '1.0.0', 'https://www.webtoons.com'),
(5, 'TakoManga', 'id', 'manhwa', '1.0.0', 'https://takomanga.com'),
(6, 'MangaToon', 'en', 'manhua', '1.0.0', 'https://mangatoon.mobi'),
(7, 'QiManga', 'en', 'manhua', '1.0.0', 'https://qimanga.com'),
(8, 'Marvel Unlimited', 'en', 'comics', '1.0.0', 'https://www.marvel.com/comics/unlimited'),
(9, 'DC Universe', 'en', 'comics', '1.0.0', 'https://www.dcuniverse.com'),
(10, 'ComiXology', 'en', 'comics', '1.0.0', 'https://www.comixology.com');

-- --------------------------------------------------------

--
-- Table structure for table `komik_genres`
--

CREATE TABLE `komik_genres` (
  `komik_id` int(11) NOT NULL,
  `genre_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `komik_genres`
--

INSERT INTO `komik_genres` (`komik_id`, `genre_id`) VALUES
(5, 1),
(5, 27),
(5, 28),
(5, 30),
(6, 5),
(6, 6),
(6, 25),
(6, 26),
(7, 1),
(7, 25),
(7, 26),
(10, 8),
(10, 11),
(10, 13),
(10, 33),
(11, 1),
(11, 2),
(11, 3),
(11, 5),
(11, 6),
(12, 1),
(12, 6),
(12, 7),
(12, 21),
(14, 1),
(14, 4),
(14, 6),
(14, 12),
(15, 3),
(15, 4),
(15, 5),
(15, 6),
(16, 1),
(16, 2),
(16, 9),
(16, 11),
(17, 1),
(17, 4),
(17, 9),
(17, 11);

-- --------------------------------------------------------

--
-- Table structure for table `library`
--

CREATE TABLE `library` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `manga_id` varchar(100) NOT NULL,
  `judul` varchar(255) DEFAULT NULL,
  `cover` text DEFAULT NULL,
  `content_type` varchar(20) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `library`
--

INSERT INTO `library` (`id`, `user_id`, `manga_id`, `judul`, `cover`, `content_type`, `created_at`) VALUES
(1, 14, 'https://myanimelist.net/manga/2/Berserk', 'Berserk', 'https://cdn.myanimelist.net/images/manga/1/157897l.jpg', 'manga', '2026-06-19 08:04:25'),
(3, 29, 'https://myanimelist.net/manga/12/Bleach', 'Bleach', 'https://cdn.myanimelist.net/images/manga/3/180031l.jpg', 'manga', '2026-06-19 08:11:17'),
(5, 14, 'https://myanimelist.net/manga/7/Hajime_no_Ippo', 'Hajime no Ippo', 'https://cdn.myanimelist.net/images/manga/2/250313l.jpg', 'manga', '2026-06-20 09:48:18');

-- --------------------------------------------------------

--
-- Table structure for table `novel`
--

CREATE TABLE `novel` (
  `id` varchar(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `site` varchar(255) DEFAULT NULL,
  `lang` varchar(10) NOT NULL DEFAULT 'en',
  `url` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `novel`
--

INSERT INTO `novel` (`id`, `name`, `site`, `lang`, `url`) VALUES
('novel-001', 'NovelUpdates', 'NovelUpdates', 'en', 'https://www.novelupdates.com');

-- --------------------------------------------------------

--
-- Table structure for table `novel_genres`
--

CREATE TABLE `novel_genres` (
  `novel_id` varchar(50) NOT NULL,
  `genre_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `novel_genres`
--

INSERT INTO `novel_genres` (`novel_id`, `genre_id`) VALUES
('novel-001', 4),
('novel-001', 6);

-- --------------------------------------------------------

--
-- Table structure for table `reading_history`
--

CREATE TABLE `reading_history` (
  `id` int(11) NOT NULL,
  `manga_id` varchar(500) NOT NULL,
  `user_id` int(11) NOT NULL DEFAULT 0,
  `judul` varchar(255) NOT NULL,
  `gambar_sampul` text DEFAULT NULL,
  `tags` varchar(500) DEFAULT NULL,
  `waktu_baca` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `reading_history`
--

INSERT INTO `reading_history` (`id`, `manga_id`, `user_id`, `judul`, `gambar_sampul`, `tags`, `waktu_baca`) VALUES
(1, '2', 14, 'Berserk', 'https://cdn.myanimelist.net/images/manga/1/157897l.jpg', 'Action,Adventure,Award Winning,Drama,Fantasy,Horror', '2026-06-20 09:48:36'),
(3, '12', 29, 'Bleach', 'https://cdn.myanimelist.net/images/manga/3/180031l.jpg', 'Action,Adventure,Award Winning,Supernatural', '2026-06-19 08:11:16'),
(5, '12', 14, 'Bleach', 'https://cdn.myanimelist.net/images/manga/3/180031l.jpg', 'Action,Adventure,Award Winning,Supernatural', '2026-06-19 08:23:11'),
(10, '2', 0, 'Berserk', 'https://cdn.myanimelist.net/images/manga/1/157897l.jpg', 'Action,Adventure,Award Winning,Drama,Fantasy,Horror', '2026-06-20 10:03:04'),
(12, '7', 14, 'Hajime no Ippo', 'https://cdn.myanimelist.net/images/manga/2/250313l.jpg', 'Award Winning,Sports', '2026-06-20 09:52:31'),
(14, '11', 0, 'Naruto', 'https://cdn.myanimelist.net/images/manga/3/249658l.jpg', 'Action,Adventure,Fantasy', '2026-06-20 09:52:15');

-- --------------------------------------------------------

--
-- Table structure for table `tags`
--

CREATE TABLE `tags` (
  `content_id` varchar(500) NOT NULL,
  `tag` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('user','admin') NOT NULL DEFAULT 'user',
  `dibuat_pada` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`, `dibuat_pada`) VALUES
(1, 'admin', 'admin123', 'admin', '2026-06-17 11:08:45'),
(14, 'rja', 'EFcvN7d8mfGFv8h', 'user', '2026-06-18 08:06:18'),
(24, 'ical', 'helmizar', 'user', '2026-06-19 06:41:19'),
(29, 'usu', 'opsu', 'user', '2026-06-19 08:10:59');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `contents`
--
ALTER TABLE `contents`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `downloaded`
--
ALTER TABLE `downloaded`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_user_manga_dl` (`user_id`,`manga_id`);

--
-- Indexes for table `genres`
--
ALTER TABLE `genres`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nama_genre` (`nama_genre`);

--
-- Indexes for table `komik`
--
ALTER TABLE `komik`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `komik_genres`
--
ALTER TABLE `komik_genres`
  ADD PRIMARY KEY (`komik_id`,`genre_id`),
  ADD KEY `genre_id` (`genre_id`);

--
-- Indexes for table `library`
--
ALTER TABLE `library`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_user_manga` (`user_id`,`manga_id`);

--
-- Indexes for table `novel`
--
ALTER TABLE `novel`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `novel_genres`
--
ALTER TABLE `novel_genres`
  ADD PRIMARY KEY (`novel_id`,`genre_id`),
  ADD KEY `genre_id` (`genre_id`);

--
-- Indexes for table `reading_history`
--
ALTER TABLE `reading_history`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `manga_user` (`manga_id`(255),`user_id`);

--
-- Indexes for table `tags`
--
ALTER TABLE `tags`
  ADD PRIMARY KEY (`content_id`,`tag`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `downloaded`
--
ALTER TABLE `downloaded`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `genres`
--
ALTER TABLE `genres`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT for table `komik`
--
ALTER TABLE `komik`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `library`
--
ALTER TABLE `library`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `reading_history`
--
ALTER TABLE `reading_history`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `komik_genres`
--
ALTER TABLE `komik_genres`
  ADD CONSTRAINT `komik_genres_ibfk_1` FOREIGN KEY (`komik_id`) REFERENCES `komik` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `komik_genres_ibfk_2` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `library`
--
ALTER TABLE `library`
  ADD CONSTRAINT `library_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `novel_genres`
--
ALTER TABLE `novel_genres`
  ADD CONSTRAINT `novel_genres_ibfk_1` FOREIGN KEY (`novel_id`) REFERENCES `novel` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `novel_genres_ibfk_2` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `tags`
--
ALTER TABLE `tags`
  ADD CONSTRAINT `tags_ibfk_1` FOREIGN KEY (`content_id`) REFERENCES `contents` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
